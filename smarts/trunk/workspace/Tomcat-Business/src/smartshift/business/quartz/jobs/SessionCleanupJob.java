package smartshift.business.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import smartshift.business.security.session.UserSessionManager;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 *  PEriodically attempts to connect to the accounts service
 */
public class SessionCleanupJob implements Job {
    private static final SmartLogger logger = new SmartLogger(SessionCleanupJob.class);

    /**
     * 
     */
    public SessionCleanupJob() { }
    
    /**
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            if(UserSessionManager.clean() > 0)
                logger.info("Cleaned up some sessions");
        } catch(Exception e) {
            logger.error("An unexpected error occured", e);
            return;
        }
    }
}
