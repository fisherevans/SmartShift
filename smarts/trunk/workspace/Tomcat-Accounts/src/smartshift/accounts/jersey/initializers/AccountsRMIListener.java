package smartshift.accounts.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import smartshift.accounts.rmi.implementation.AccountsService;
import smartshift.common.rmi.RMIServer;
import smartshift.common.util.properties.AppConstants;

/**
 * @author fevans
 * 
 *          the listener that starts the RMI service
 */
public class AccountsRMIListener implements ServletContextListener {  
    private static final Logger logger = Logger.getLogger(AccountsRMIListener.class);
    
    /**
     * a context has been initialized, create the RMI service
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {   
        try {
            RMIServer.start(AppConstants.RMI_ACCOUNTS_PORT);
            RMIServer.create(AccountsService.class, AppConstants.RMI_ACCOUNTS_SERVICE_NAME);
        } catch(Exception e) {
            logger.error("Failed to create accounts RMI service", e);
        }
    }  

    /**
     * a context has been destroyed, destroy the RMI service
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            RMIServer.destroy(AppConstants.RMI_ACCOUNTS_SERVICE_NAME);
        } catch(Exception e) {
            logger.error("Failed to destroy accounts RMI service", e);
        }
    }
}
