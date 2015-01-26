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
     * @param businessID the business id
     * @param timoutPeriod the time in ms the sessions should expire in
     * @throws RemoteException
     */
    public void addUserSession(String username, String sessionId, Integer businessID, long timoutPeriod) throws RemoteException;
    
    /**
     * Removes the given sessions id
     * @param sessionId the sessions id to remove
     * @return true if a sessions was removed
     * @throws RemoteException
     */
    public boolean removeUserSession(String sessionId) throws RemoteException;
    
    /**
     * Invalidate all current user sessions
     * @param businessID the business id of the sessions to close. pass null to invalidate all
     * @return the number of sessions invalidated
     * @throws RemoteException
     */
    public int invalidateAllUserSessions(Integer businessID) throws RemoteException;
    
    /**
     * Connects this server to this business via the database
     * @param businessID the business id
     * @param businessName the business name
     * @throws RemoteException
     */
    public void connectBusinessSchema(Integer businessID, String businessName) throws RemoteException;

    /**
     * Called by the accouts application to tell the business app the server is disconnecting
     */
    public void disconnecting() throws RemoteException;
}
