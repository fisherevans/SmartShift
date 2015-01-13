package smartshift.common.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * A base class that that holds some more information about the remote service
 */
public interface BaseRemoteInterface extends Remote {
    /**
     * @param serverHostname The RMI Sever this remote is connected from
     * @param serverPort The port the RMI server is running on
     * @param serviceName The RMI service name
     * @throws RemoteException If any exception is thrown
     */
    public void setRMIInfo(String serverHostname, Integer serverPort, String serviceName) throws RemoteException;
    
    /**
     * @return The RMI service name
     * @throws RemoteException If any exception is thrown
     */
    public String getRMIServiceName() throws RemoteException;
    
    /**
     * @return The RMI Sever this remote is connected from
     * @throws RemoteException If any exception is thrown
     */
    public String getRMIServerHostname() throws RemoteException;
    
    /**
     * @return The port the RMI server is running on
     * @throws RemoteException If any exception is thrown
     */
    public Integer getRMIServerPort() throws RemoteException;

    /**
     * @return A human friendly string describing the RMI information
     * @throws RemoteException If any exception is thrown
     */
    public String getRMIInfo() throws RemoteException;

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

    /**
     * Called by the to test the connection status
     * @return always returns "pong"
     * @throws RemoteException 
     */
    public String ping() throws RemoteException;
}
