package smartshift.business.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.JobDataMap;
import smartshift.business.cache.bo.Cache;
import smartshift.business.quartz.jobs.AccountsConnectionJob;
import smartshift.business.quartz.jobs.SessionCleanupJob;
import smartshift.common.hibernate.dao.HibernateTaskQueue;
import smartshift.common.quartz.QuartzHelper;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * A servlet to clean up resource before the system is brought down
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
public class TopBusinessListener implements ServletContextListener {
    private static final SmartLogger logger = new SmartLogger(TopBusinessListener.class);

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        QuartzHelper.createRepeatingJob(AccountsConnectionJob.class,
                "accountsConnection", AppConstants.QUARTZ_BUSINESS_GROUP,
                "rmi.connection.polling", 60, new JobDataMap());
        QuartzHelper.createRepeatingJob(SessionCleanupJob.class,
                "sessionCleanup", AppConstants.QUARTZ_BUSINESS_GROUP,
                "sessions.polling", 60, new JobDataMap());
    }

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateTaskQueue.closeAllQueues();
        QuartzHelper.stopAllJobs();
        Cache.saveAllCaches();
    }

}
