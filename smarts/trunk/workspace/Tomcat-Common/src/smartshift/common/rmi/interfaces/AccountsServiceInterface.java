package smartshift.common.rmi.interfaces;

import java.rmi.RemoteException;

/**
 * The interface for the accounts service
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public interface AccountsServiceInterface extends BaseRemoteInterface {
    /**
     * gets the global next id to use
     * @param name the name of the id to get
     * @return the next id to use, null if there was an error
     * @throws RemoteException if an exception occurs
     */
    public Integer getNextID(String name) throws RemoteException;

    /**
     * Called by the client informing the server i has connected
     * @param clientHostname The hostname of the client connected to the server
     * @param clientPort the client rmi port to connect to
     * @param developmentBusinesses Any business to register this server under
     * @throws RemoteException If any exception is thrown
     */
    public void businessConnected(String clientHostname, int clientPort, Integer ... developmentBusinesses) throws RemoteException;

    /**
     * Called by the client informing the server is disconnecting
     * @param clientHostname The hostname of the client connected to the server
     * @param clientPort the client rmi port connected
     * @throws RemoteException If any exception is thrown
     */
    public void businessDisconnecting(String clientHostname, int clientPort) throws RemoteException;
}