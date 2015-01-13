package smartshift.business.rmi.implementation;

import java.rmi.RemoteException;
import smartshift.common.rmi.BaseRemote;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.util.properties.AppConstants;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * implementation of the accounts RMI service
 */
public class BusinessService extends BaseRemote implements BusinessServiceInterface {
    /**
     * 
     */
    private static final long serialVersionUID = -1647486952709539166L;

    /** Initializes the service
     * @throws RemoteException
     */
    public BusinessService() throws RemoteException {
        super();
    }

    @Override
    public double getCost() throws RemoteException {
        return 100.01;
    }

    @Override
    public void connected(String clientHostname, int clientPort) throws RemoteException {
        System.out.println("Business:" + AppConstants.CONTEXT_PATH + " says - " + clientHostname + ":" + clientPort + " has connected.");
    }

    @Override
    public void disconnecting(String clientHostname, int clientPort) throws RemoteException {
        System.out.println("Business:" + AppConstants.CONTEXT_PATH + " says - " + clientHostname + ":" + clientPort + " is disconnecting.");
    }
}