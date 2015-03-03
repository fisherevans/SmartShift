package smartshift.accounts.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.NextIDDAO;

/**
 * A servlet to clean up resource before the system is brought down
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
public class CleanupListener implements ServletContextListener {

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
        AccountsDAOContext.dao(NextIDDAO.class).saveNextIDs();
    }

}
