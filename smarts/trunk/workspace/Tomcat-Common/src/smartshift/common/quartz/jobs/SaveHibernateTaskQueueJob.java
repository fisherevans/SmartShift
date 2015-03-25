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

public class SaveHibernateTaskQueueJob implements Job {
    private static final SmartLogger logger = new SmartLogger(SaveHibernateTaskQueueJob.class);
    
    private static final Map<String, HibernateTaskQueue> queueMap = new HashMap<>();;

    public static final String JOB_GROUP_NAME = "saveHibernateTaskQueueJobs";
    
    public static final String JOB_CONTEXT_ID = "contextID";
    
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
    
    public static String getJobName(DAOContext context) {
        return JOB_GROUP_NAME + context.getContextID().toString();
    }
    
    public static JobDataMap getJobData(HibernateTaskQueue queue, DAOContext context) {
        queueMap.put(context.getContextID().toString(), queue);
        JobDataMap data = new JobDataMap();
        data.put(JOB_CONTEXT_ID, context.getContextID().toString());
        return data;
    }
}
