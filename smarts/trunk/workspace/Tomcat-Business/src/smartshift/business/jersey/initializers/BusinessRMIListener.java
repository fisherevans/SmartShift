package smartshift.business.jersey.initializers;


import java.rmi.RemoteException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.business.rmi.implementation.BusinessService;
import smartshift.common.rmi.RMIClient;
import smartshift.common.rmi.RMIServer;
import smartshift.common.rmi.interfaces.AccountsServiceInterface;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * @author fevans
 * 
 *          the listener that starts the RMI service
 */
public class BusinessRMIListener implements ServletContextListener {  
    private static final SmartLogger logger = new SmartLogger(BusinessRMIListener.class);
    
    /**
     * a context has been initialized, create the RMI service
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {   
        try {
            RMIServer.start(AppConstants.RMI_BUSINESS_PORT);
            RMIServer.create(BusinessService.class, AppConstants.RMI_BUSINESS_SERVICE_NAME);
        } catch(Exception e) {
            logger.error("Failed to create business RMI service", e);
        }
    }  

    /**
     * a context has been destroyed, destroy the RMI service
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            if(RMIClient.isClinetStarted(AppConstants.RMI_ACCOUNTS_HOSTNAME, AppConstants.RMI_ACCOUNTS_PORT)) {
                AccountsServiceInterface acctsService = (AccountsServiceInterface)
                        RMIClient.getService(AppConstants.RMI_ACCOUNTS_HOSTNAME, AppConstants.RMI_ACCOUNTS_PORT, AppConstants.RMI_ACCOUNTS_SERVICE_NAME);
                acctsService.businessDisconnecting(AppConstants.HOSTNAME, AppConstants.RMI_BUSINESS_PORT);
            }
        } catch(Exception e) {
            logger.error("Failed to warn accounts app of shutdown!", e);
        }
        
        try {
            RMIServer.destroyAll();
        } catch(RemoteException e) {
            logger.error("Failed to shut down RMI server!", e);
        }
    }
}
