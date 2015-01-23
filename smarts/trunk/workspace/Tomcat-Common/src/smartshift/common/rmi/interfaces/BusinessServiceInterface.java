package smartshift.common.rmi.interfaces;

import java.rmi.RemoteException;

/**
 * The business RMI service
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public interface BusinessServiceInterface extends BaseRemoteInterface {
    /**
     * Adds a user sessions to the business service
     * @param username the username for the session
     * @param sessionId  the session id
     * @param timoutPeriod the time in ms the sessions should expire in
     * @throws RemoteException
     */
    public void addUserSession(String username, String sessionId, long timoutPeriod) throws RemoteException;
    
    /**
     * Removes the given sessions id
     * @param sessionId the sessions id to remove
     * @return true if a sessions was removed
     */
    public boolean removeUserSession(String sessionId);
    
    /**
     * Invalidate all current user sessions
     * @return the number of sessions invalidated
     */
    public int invalidateAllUserSessions();
}
