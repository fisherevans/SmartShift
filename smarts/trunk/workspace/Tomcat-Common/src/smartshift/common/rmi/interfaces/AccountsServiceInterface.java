package smartshift.common.rmi.interfaces;

import java.rmi.RemoteException;

/**
 * The interface for the accounts service
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public interface AccountsServiceInterface extends BaseRemoteInterface {
    /**
     * A test method
     * @return a string
     * @throws RemoteException
     */
    public String hello() throws RemoteException;

    /**
     * Called by the client informing the server i has connected
     * @param clientHostname The hostname of the client connected to the server
     * @param clientPort the client rmi port to connect to
     * @throws RemoteException If any exception is thrown
     */
    public void connected(String clientHostname, int clientPort) throws RemoteException;

    /**
     * Called by the client informing the server is disconnecting
     * @param clientHostname The hostname of the client connected to the server
     * @param clientPort the client rmi port connected
     * @throws RemoteException If any exception is thrown
     */
    public void disconnecting(String clientHostname, int clientPort) throws RemoteException;
}