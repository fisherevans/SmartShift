package smartshift.common.quartz.jobs;

import java.util.HashMap;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import smartshift.common.hibernate.DAOContext;
import smartshift.common.hibernate.dao.HibernateTaskQueue;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author fevans
 * a job to run hibernate task queues
 */
public class SaveHibernateTaskQueueJob implements Job {
    private static final SmartLogger logger = new SmartLogger(SaveHibernateTaskQueueJob.class);
    
    private static final Map<String, HibernateTaskQueue> queueMap = new HashMap<>();;

    /**
     * the group name for jobs using this
     */
    public static final String JOB_GROUP_NAME = "saveHibernateTaskQueueJobs";
    
    /**
     * the param name used for passing the dao context id
     */
    public static final String JOB_CONTEXT_ID = "contextID";
    
    /**
     * empty
     */
    public SaveHibernateTaskQueueJob() { }
    
    /**
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Object contextID = context.get(JOB_CONTEXT_ID);
            HibernateTaskQueue queue = queueMap.get(contextID);
            queue.processAllTasks();
        } catch(Exception e) {
            logger.error("An unexpected error occured", e);
        }
    }
    
    /**
     * @param context the dao context to generate a job name for
     * @return the name to use
     */
    public static String getJobName(DAOContext context) {
        return JOB_GROUP_NAME + context.getContextID().toString();
    }
    
    /**
     * @param queue the queue to process with thei job
     * @param context the queue's dao
     * @return the param data to create the job with
     */
    public static JobDataMap getJobData(HibernateTaskQueue queue, DAOContext context) {
        queueMap.put(context.getContextID().toString(), queue);
        JobDataMap data = new JobDataMap();
        data.put(JOB_CONTEXT_ID, context.getContextID().toString());
        return data;
    }
}
