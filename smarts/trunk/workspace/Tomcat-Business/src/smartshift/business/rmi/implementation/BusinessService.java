package smartshift.business.rmi.implementation;

import java.rmi.RemoteException;
import org.dom4j.IllegalAddException;
import smartshift.common.hibernate.BusinessDatabaseManager;
import smartshift.common.rmi.BaseRemote;
import smartshift.common.rmi.RMIClient;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.security.session.UserSession;
import smartshift.common.security.session.UserSessionManager;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * implementation of the accounts RMI service
 */
public class BusinessService extends BaseRemote implements BusinessServiceInterface {
    private static SmartLogger logger = new SmartLogger(BusinessService.class);
    
    private static final long serialVersionUID = -1647486952709539166L;

    /** Initializes the service
     * @throws RemoteException
     */
    public BusinessService() throws RemoteException {
        super();
    }

    /**
     * @see smartshift.common.rmi.interfaces.BusinessServiceInterface#addUserSession(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, long)
     */
    @Override
    public void addUserSession(String username, String sessionId, Integer businessID, Integer employeeID, long timoutPeriod) throws RemoteException {
        logger.info("Adding session: " + sessionId);
        UserSession session = new UserSession(username, sessionId, businessID, employeeID, timoutPeriod);
        try {
            UserSessionManager.addSession(session);
        } catch(IllegalAddException e) {
            logger.error("Failed to add session: " + sessionId, e);
            throw new RemoteException("Session already exists", e);
        }
    }

    /**
     * @see smartshift.common.rmi.interfaces.BusinessServiceInterface#removeUserSession(java.lang.String)
     */
    @Override
    public boolean removeUserSession(String sessionId) throws RemoteException {
        return UserSessionManager.removeSession(sessionId) != null;
    }

    /**
     * @see smartshift.common.rmi.interfaces.BusinessServiceInterface#invalidateAllUserSessions(java.lang.Integer)
     */
    @Override
    public int invalidateAllUserSessions(Integer businessID) throws RemoteException {
        return UserSessionManager.invalidateAllSessions(businessID);
    }
    
    /**
     * @see smartshift.common.rmi.interfaces.BusinessServiceInterface#connectBusinessSchema(java.lang.Integer, java.lang.String)
     */
    @Override
    public boolean connectBusinessSchema(Integer businessID, String businessName) throws RemoteException {
        return BusinessDatabaseManager.connectBusinessSchema(businessID, businessName);
    }

    /**
     * @see smartshift.common.rmi.interfaces.BusinessServiceInterface#accountsDisconnecting()
     */
    @Override
    public void accountsDisconnecting() throws RemoteException {
        logger.warn("Accounts application is disconnecting!");
        RMIClient.stopClient(AppConstants.RMI_ACCOUNTS_HOSTNAME, AppConstants.RMI_ACCOUNTS_PORT);
        BusinessDatabaseManager.disconnectAllSchemas(AppConstants.DEV_BUSINESS_MANUAL_BUSINESSES);
    }
}