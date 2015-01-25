package smartshift.business.rmi.implementation;

import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import org.dom4j.IllegalAddException;
import smartshift.common.hibernate.BusinessConnectionManager;
import smartshift.common.rmi.BaseRemote;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.security.session.UserSession;
import smartshift.common.security.session.UserSessionManager;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * implementation of the accounts RMI service
 */
public class BusinessService extends BaseRemote implements BusinessServiceInterface {
    private static Logger logger = Logger.getLogger(BusinessService.class);
    
    private static final long serialVersionUID = -1647486952709539166L;

    /** Initializes the service
     * @throws RemoteException
     */
    public BusinessService() throws RemoteException {
        super();
    }

    /**
     * @see smartshift.common.rmi.interfaces.BusinessServiceInterface#addUserSession(java.lang.String, java.lang.String, java.lang.Integer, long)
     */
    @Override
    public void addUserSession(String username, String sessionId, Integer businessID, long timoutPeriod) throws RemoteException {
        logger.info("Adding session: " + sessionId);
        UserSession session = new UserSession(username, sessionId, businessID, timoutPeriod);
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
    public void connectBusinessSchema(Integer businessID, String businessName) throws RemoteException {
        BusinessConnectionManager.connectBusinessSchema(businessID, businessName);
    }
}