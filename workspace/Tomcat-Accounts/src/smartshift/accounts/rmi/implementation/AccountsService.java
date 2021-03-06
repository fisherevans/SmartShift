package smartshift.accounts.rmi.implementation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.NextIDDAO;
import smartshift.accounts.hibernate.dao.ServerDAO;
import smartshift.accounts.hibernate.dao.SessionDAO;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.accounts.hibernate.model.ServerModel;
import smartshift.accounts.hibernate.model.custom.GetActiveSessionsModel;
import smartshift.accounts.rmi.BusinessServiceManager;
import smartshift.common.rmi.BaseRemote;
import smartshift.common.rmi.RMIClient;
import smartshift.common.rmi.interfaces.AccountsServiceInterface;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.util.PrimativeUtils;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * implementation of the accounts RMI service
 *
 */
public class AccountsService extends BaseRemote implements AccountsServiceInterface {
    private static final long serialVersionUID = -2820243699796645149L;
    
    private static final SmartLogger logger = new SmartLogger(AccountsService.class);
    
    /** Initialized the service
     * @throws RemoteException
     */
    public AccountsService() throws RemoteException {
        super();
    }

    /**
     * @see smartshift.common.rmi.interfaces.AccountsServiceInterface#getNextGlobalID(java.lang.String)
     */
    @Override
    public Integer getNextGlobalID(String name) throws RemoteException {
        try {
            Integer id = AccountsDAOContext.dao(NextIDDAO.class).getNextID(name);
            return id;
        } catch(Exception e) {
            logger.error("Failed to get next id for " + name, e);
            return null;
        }
    }

    /**
     * @see smartshift.common.rmi.interfaces.AccountsServiceInterface#businessConnected(java.lang.String, int, java.lang.Integer[])
     */
    @Override
    public void businessConnected(String clientHostname, int clientPort, Integer ... developmentBusinesses) throws RemoteException {
        logger.info(clientHostname + ":" + clientPort + " has connected via RMI.");
        ServerModel server = AccountsDAOContext.dao(ServerDAO.class).uniqueByHostname(clientHostname);
        if(server == null)
            logger.warn("An application has connected that is not registered in the database: " + clientHostname);
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
            throw new RemoteException("Busines server could not be connected to - not registering businesses");
        }
        List<Integer> businessIDs = new ArrayList<>();
        // Dev businesses
        if(developmentBusinesses.length > 0 || server == null) {
            logger.warn(clientHostname + " requested the following businesses for development: " + PrimativeUtils.joinArray(developmentBusinesses, " "));
            businessIDs.addAll(Arrays.asList(developmentBusinesses));
        } else { // registered db servers
            if(server.getBusinesses().size() > 0) {
                for(BusinessModel business:server.getBusinesses()) {
                    if(businessService.connectBusinessSchema(business.getId(), business.getName())) {
                        businessIDs.add(business.getId());
                    }
                }
            }
        }
        if(businessService != null) {
            BusinessServiceManager.addService(businessService, clientHostname, businessIDs.toArray(new Integer[0]));
            Date minLastAccess = new Date(System.currentTimeMillis() - AppConstants.SESSION_TIMEOUT);
            for(Integer businessID:businessIDs) {
                try {
                    businessService.invalidateAllUserSessions(businessID);
                    ROCollection<GetActiveSessionsModel> sessions = AccountsDAOContext.dao(SessionDAO.class).listByBusinessAccess(businessID, minLastAccess).execute();
                    for(GetActiveSessionsModel session:sessions) {
                        businessService.addUserSession(session.username, session.sessionKey, session.businessID,
                                session.employeeID, session.lastActivity.getTime(), AppConstants.SESSION_TIMEOUT);
                    }
                } catch(Exception e) {
                    logger.warn("Failed to send existing active sessions to business app", e);
                }
            }
        } else {
            logger.warn("For some reason the service was null - please, investigate " + clientHostname + ":" + clientPort);
        }
    }

    /**
     * @see smartshift.common.rmi.interfaces.AccountsServiceInterface#businessDisconnecting(java.lang.String, int)
     */
    @Override
    public void businessDisconnecting(String clientHostname, int clientPort) throws RemoteException {
        logger.info(clientHostname + ":" + clientPort + " is disconnecting from RMI.");
        BusinessServiceManager.removeService(clientHostname);
    }
}
