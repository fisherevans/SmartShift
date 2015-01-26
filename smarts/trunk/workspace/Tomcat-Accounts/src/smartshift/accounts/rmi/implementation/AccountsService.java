package smartshift.accounts.rmi.implementation;

import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import smartshift.accounts.rmi.BusinessServiceManager;
import smartshift.common.hibernate.dao.accounts.ServerDAO;
import smartshift.common.hibernate.model.accounts.BusinessModel;
import smartshift.common.hibernate.model.accounts.ServerModel;
import smartshift.common.rmi.BaseRemote;
import smartshift.common.rmi.RMIClient;
import smartshift.common.rmi.interfaces.AccountsServiceInterface;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.util.PrimativeUtils;
import smartshift.common.util.properties.AppConstants;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * implementation of the accounts RMI service
 *
 */
public class AccountsService extends BaseRemote implements AccountsServiceInterface {
    private static final long serialVersionUID = -2820243699796645149L;
    
    private static final Logger logger = Logger.getLogger(AccountsService.class);
    
    /** Initialized the service
     * @throws RemoteException
     */
    public AccountsService() throws RemoteException {
        super();
    }
    
    /**
     * @see smartshift.common.rmi.interfaces.AccountsServiceInterface#hello()
     */
    @Override
    public String hello() throws RemoteException {
        return "The answer to everything is 42";
    }

    /**
     * @see smartshift.common.rmi.interfaces.AccountsServiceInterface#businessConnected(java.lang.String, int)
     */
    @Override
    public void businessConnected(String clientHostname, int clientPort, Integer ... developmentBusinesses) throws RemoteException {
        logger.info(clientHostname + ":" + clientPort + " has connected via RMI.");
        ServerModel server = ServerDAO.getServerByHostname(clientHostname);
        if(server == null) {
            logger.warn("An application has connected that is not registered in the database: " + clientHostname);
            //throw new RemoteException("Server not registered in the DB!");
        }
        BusinessServiceInterface businessService = null;
        try {
            if(!RMIClient.isClinetStarted(clientHostname, clientPort))
                RMIClient.startClient(clientHostname, clientPort);
            if(RMIClient.isServiceConnected(clientHostname, clientPort, AppConstants.RMI_BUSINESS_SERVICE_NAME))
                businessService = (BusinessServiceInterface) RMIClient.getService(clientHostname, clientPort, AppConstants.RMI_BUSINESS_SERVICE_NAME);
            else
                businessService = (BusinessServiceInterface) RMIClient.connectService(clientHostname, clientPort, AppConstants.RMI_BUSINESS_SERVICE_NAME);
        } catch(Exception e) {
            logger.warn("Failed to connect back to the business service" + clientHostname, e);
            throw new RemoteException("Busines server could not e connected to");
        }
        // Dev businesses
        if(developmentBusinesses.length > 0) {
            logger.warn(clientHostname + " requested the following businesses: " + PrimativeUtils.joinArray(developmentBusinesses, " "));
            BusinessServiceManager.addService(businessService, clientHostname, developmentBusinesses);
        } else { // registered db servers
            if(server.getBusinesses().size() > 0) {
                Integer[] businessIDs = new Integer[server.getBusinesses().size()];
                int id = 0;
                for(BusinessModel business:server.getBusinesses()) {
                    businessService.connectBusinessSchema(business.getId(), business.getName());
                    businessIDs[id++] = business.getId();
                }
                BusinessServiceManager.addService(businessService, clientHostname, businessIDs);
            }
        }
    }

    /**
     * @see smartshift.common.rmi.interfaces.AccountsServiceInterface#businessDisconnecting(java.lang.String, int)
     */
    @Override
    public void businessDisconnecting(String clientHostname, int clientPort) throws RemoteException {
        logger.info("Accounts:" + AppConstants.CONTEXT_PATH + " says - " + clientHostname + ":" + clientPort + " is disconnecting.");
        BusinessServiceManager.removeService(clientHostname);
    }
}
