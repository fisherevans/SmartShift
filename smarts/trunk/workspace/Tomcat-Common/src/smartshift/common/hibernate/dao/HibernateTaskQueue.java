package smartshift.common.hibernate.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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

/**
 * @author fevans
 * used to queue db tasks
 */
public class HibernateTaskQueue {
    private static final SmartLogger logger = new SmartLogger(HibernateTaskQueue.class);
    
    private static Map<Object, HibernateTaskQueue> queues = null;
    
    private final Boolean ADD_LOCK = true, SCHEDULE_LOCK = true;
    
    private Boolean active = true;
    
    private List<EnqueuedTaskWrapper> _incomingTasks;
    
    private List<EnqueuedTaskWrapper> _scheduledTasks;
    
    private JobKey _jobKey;
    
    private final DAOContext _daoContext;
    
    /** creates the queue
     * @param daoContext the dao context to run the tasks on
     */
    public HibernateTaskQueue(DAOContext daoContext) {
        _daoContext = daoContext;
        _incomingTasks = new LinkedList<>();
        _scheduledTasks = new ArrayList<>();
    }
    
    /** enqueues a task
     * @param baseTask the task to add
     */
    @SuppressWarnings("rawtypes")
    public void enqueueTask(BaseHibernateTask baseTask) {
        synchronized(ADD_LOCK) {
            if(!active)
                throw new RuntimeException("This queue is no longer active!");
            EnqueuedTaskWrapper wrapper = new EnqueuedTaskWrapper(baseTask);
            _incomingTasks.add(wrapper);
        }
    }
    
    /**
     * processes all adds
     * runs all scheduled tasks
     */
    public void processAllTasks() {
        processAddedTasks();
        processScheduledTasks();
        logger.debug("DB task queue has been flushed to the DB for: " + _daoContext.getContextID());
    }
    
    /**
     * runs through the add queue, and checks for any that may cancel out an existing task. if it does, removed both
     * adds remaining to schedule queue (to be run)
     */
    public void processAddedTasks() {
        logger.debug("Processing added tasks for: " + _daoContext.getContextID());
        List<EnqueuedTaskWrapper> incommingTasks = null;
        synchronized(ADD_LOCK) {
            incommingTasks = _incomingTasks;
            _incomingTasks = new LinkedList<>();
        }
        synchronized(SCHEDULE_LOCK) {
            // @SuppressWarnings("rawtypes")
            // BaseHibernateTask incommingTask, scheduledTask, compoundTask;
            for(EnqueuedTaskWrapper incommingWrapper:incommingTasks) {
                _scheduledTasks.add(incommingWrapper);
                /** TODO - cancel out stuff if possible
                incommingTask = incommingWrapper.getTask();
                boolean addTask = true, checkCompounds = true;
                ListIterator<EnqueuedTaskWrapper> scheduledIttr = _scheduledTasks.listIterator(_scheduledTasks.size()); // get ittr starting at last position
                while(scheduledIttr.hasPrevious()) { // go backwards
                    EnqueuedTaskWrapper scheduledWrapper = scheduledIttr.previous();
                    scheduledTask = scheduledWrapper.getTask();
                    if(incommingTask.equalizes(scheduledTask)) {
                        scheduledIttr.remove();
                        addTask = false;
                        break;
                    }
                    if(incommingTask.overrides(scheduledTask)) {
                        scheduledIttr.remove();
                        continue;
                    }
                    if(checkCompounds) {
                        // can only check sequential tasks as they're added starting with the most recent scheduled one due to critical race issues
                        compoundTask = incommingTask.compounds(scheduledTask);
                        if(compoundTask != null) {
                            scheduledWrapper.setTask(compoundTask);
                            addTask = false;
                            break;
                        } else {
                            checkCompounds = false;
                        }
                    }
                }
                if(addTask)
                    _scheduledTasks.add(incommingWrapper); */
            }
        }
    }
    
    /**
     * actually runs all tasks that are scheduled to run
     */
    public void processScheduledTasks() {
        logger.debug("Running scheduled tasks for: " + _daoContext.getContextID());
        synchronized(SCHEDULE_LOCK) {
            // Collections.sort(_scheduledTasks);
            Session session = null;
            Transaction transaction = null;
            try {
                session = _daoContext.getSession();
                transaction = session.beginTransaction();
                for(EnqueuedTaskWrapper task:_scheduledTasks) {
                    logger.info("Executing: " + task.getTask().toString());
                    task.getTask().executeWithSession(session);
                }
                try {
                    transaction.commit();
                    session.flush();
                    session.close();
                } catch(Exception e) {
                    logger.fatal("Failed to close the hibernate connection after all tasks ran successfully!", e);
                    throw e;
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
    
    /**
     * sets the queue to inactive - preventing any additional tasks from being added
     * processes any remaining add
     * runs and scheduled tasks
     */
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
    
    /**
     * @return the dao context
     */
    public DAOContext getDaoContext() {
        return _daoContext;
    }

    /**
     * @return the job key corresponding to the job that runs this queue
     */
    public JobKey getJobKey() {
        return _jobKey;
    }

    /**
     * @param jobKey set the job key
     */
    public void setJobKey(JobKey jobKey) {
        _jobKey = jobKey;
    }

    /**
     * @param context the dao context for the queue you're after
     * @return the queue object
     */
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
    
    /**
     * runs closeQueue() on all queues
     */
    public static final void closeAllQueues() {
        if(queues == null)
            return;
        for(Object contextID:queues.keySet()) {
            HibernateTaskQueue queue = queues.get(contextID);
            queue.closeQueue();
        }
    }

    @SuppressWarnings("rawtypes")
    private static class EnqueuedTaskWrapper implements Comparable<EnqueuedTaskWrapper> {
        private BaseHibernateTask _task;
        
        private final Date _timestamp;
        
        public EnqueuedTaskWrapper(BaseHibernateTask task) {
            _task = task;
            _timestamp = new Date();
        }

        public BaseHibernateTask getTask() {
            return _task;
        }

        public void setTask(BaseHibernateTask task) {
            _task = task;
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
