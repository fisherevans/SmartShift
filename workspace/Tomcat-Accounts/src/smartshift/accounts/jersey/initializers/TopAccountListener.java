package smartshift.accounts.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.NextIDDAO;
import smartshift.common.hibernate.dao.HibernateTaskQueue;

/**
 * A servlet to clean up resource before the system is brought down
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
public class TopAccountListener implements ServletContextListener {

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateTaskQueue.closeAllQueues();
        AccountsDAOContext.dao(NextIDDAO.class).saveNextIDs();
    }

}
