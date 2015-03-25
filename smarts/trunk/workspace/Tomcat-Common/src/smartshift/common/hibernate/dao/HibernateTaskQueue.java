package smartshift.common.hibernate.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import smartshift.common.hibernate.DAOContext;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.quartz.QuartzHelper;
import smartshift.common.quartz.jobs.SaveHibernateTaskQueueJob;
import smartshift.common.util.log4j.SmartLogger;

public class HibernateTaskQueue {
    private static final SmartLogger logger = new SmartLogger(HibernateTaskQueue.class);
    
    private static Map<Object, HibernateTaskQueue> queues = null;
    
    private final Boolean ADD_LOCK = true, SCHEDULE_LOCK = true;
    
    private Boolean active = true;
    
    private List<EnqueuedTaskWrapper> _incomingTasks;
    
    private List<EnqueuedTaskWrapper> _scheduledTasks;
    
    private JobKey _jobKey;
    
    private final DAOContext _daoContext;
    
    public HibernateTaskQueue(DAOContext daoContext) {
        _daoContext = daoContext;
        _incomingTasks = new LinkedList<>();
        _scheduledTasks = new ArrayList<>();
    }
    
    public void enqueueTask(BaseHibernateTask baseTask) {
        synchronized(ADD_LOCK) {
            if(!active)
                throw new RuntimeException("This queue is no longer active!");
            EnqueuedTaskWrapper wrapper = new EnqueuedTaskWrapper(baseTask);
            _incomingTasks.add(wrapper);
        }
    }
    
    public void processAllTasks() {
        processAddedTasks();
        processScheduledTasks();
        logger.info("DB task queue has been flushed to the DB for: " + _daoContext.getContextID());
    }
    
    public void processAddedTasks() {
        logger.info("Processing added tasks for: " + _daoContext.getContextID());
        List<EnqueuedTaskWrapper> incommingTasks = null;
        synchronized(ADD_LOCK) {
            incommingTasks = _incomingTasks;
            _incomingTasks = new LinkedList<>();
        }
        synchronized(SCHEDULE_LOCK) {
            BaseHibernateTask incommingTask, baseTaskSched;
            for(EnqueuedTaskWrapper incommingWrapper:incommingTasks) {
                incommingTask = incommingWrapper.getTask();
                boolean addTask = true;
                Iterator<EnqueuedTaskWrapper> scheduledIttr = _scheduledTasks.iterator();
                while(scheduledIttr.hasNext()) {
                    EnqueuedTaskWrapper scheduledWrapper = scheduledIttr.next();
                    baseTaskSched = scheduledWrapper.getTask();
                    if(incommingTask.cancelsOut(baseTaskSched)) {
                        scheduledIttr.remove();
                        addTask = false;
                        break;
                    }
                }
                if(addTask)
                    _scheduledTasks.add(incommingWrapper);
            }
        }
    }
    
    public void processScheduledTasks() {
        logger.info("Running scheduled tasks for: " + _daoContext.getContextID());
        synchronized(SCHEDULE_LOCK) {
            Collections.sort(_scheduledTasks);
            Session session = null;
            Transaction transaction = null;
            try {
                session = _daoContext.getSession();
                transaction = session.beginTransaction();
                for(EnqueuedTaskWrapper task:_scheduledTasks)
                    task.getTask().executeWithSession(session);
                try {
                    transaction.commit();
                    session.flush();
                    session.close();
                } catch(Exception e) {
                    logger.fatal("Failed to close the hibernate connection after all tasks ran successfully!", e);
                }
                _scheduledTasks.clear();
            } catch(Exception e) {
                logger.error("There was an error running the scheduled DB tasks!", e);
                try {
                    if(transaction != null)
                        transaction.rollback();
                    if(session != null)
                        session.close();
                } catch(Exception e2) {
                    logger.fatal("Failed to close the hibernate connection after an error occured!", e2);
                }
            }
        }
    }
    
    public void closeQueue() {
        synchronized(ADD_LOCK) {
            synchronized(SCHEDULE_LOCK) {
                active = false;
            }
        }
        QuartzHelper.unscheduleJob(_jobKey);
        processAddedTasks();
        processScheduledTasks();
    }
    
    public DAOContext getDaoContext() {
        return _daoContext;
    }

    public JobKey getJobKey() {
        return _jobKey;
    }

    public void setJobKey(JobKey jobKey) {
        _jobKey = jobKey;
    }

    public static final HibernateTaskQueue getQueue(DAOContext context) {
        if(queues == null)
            queues = new HashMap<>();
        HibernateTaskQueue queue = queues.get(context.getContextID());
        if(queue == null) {
            queue = new HibernateTaskQueue(context);
            queues.put(context.getContextID(), queue);
            JobDataMap jobData = SaveHibernateTaskQueueJob.getJobData(queue, context);
            JobKey jobKey = QuartzHelper.createRepeatingJob(SaveHibernateTaskQueueJob.class,
                    SaveHibernateTaskQueueJob.getJobName(context),
                    SaveHibernateTaskQueueJob.JOB_GROUP_NAME,
                    "hibernate.queue.savePolling", 60, jobData);
            queue.setJobKey(jobKey);
        }
        return queue;
    }
    
    public static final void closeAllQueues() {
        if(queues == null)
            return;
        for(Object contextID:queues.keySet()) {
            HibernateTaskQueue queue = queues.get(contextID);
            queue.closeQueue();
        }
    }
    
    private static class EnqueuedTaskWrapper implements Comparable<EnqueuedTaskWrapper> {
        private final BaseHibernateTask _task;
        
        private final Date _timestamp;
        
        public EnqueuedTaskWrapper(BaseHibernateTask task) {
            _task = task;
            _timestamp = new Date();
        }

        public BaseHibernateTask getTask() {
            return _task;
        }

        public Date getTimestamp() {
            return _timestamp;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof EnqueuedTaskWrapper)
                return getTask().equals(((EnqueuedTaskWrapper)obj).getTask());
            else
                return false;
        }

        @Override
        public int hashCode() {
            return _task.hashCode();
        }

        @Override
        public int compareTo(EnqueuedTaskWrapper o) {
            return getTimestamp().compareTo(o.getTimestamp());
        }
    }
}
