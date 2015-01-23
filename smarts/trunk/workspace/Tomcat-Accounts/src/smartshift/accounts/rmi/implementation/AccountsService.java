package smartshift.accounts.rmi.implementation;

import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import smartshift.common.rmi.BaseRemote;
import smartshift.common.rmi.interfaces.AccountsServiceInterface;
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
    public String hello() {
        return "The answer to everything is 42";
    }

    /**
     * @see smartshift.common.rmi.interfaces.BaseRemoteInterface#connected(java.lang.String, int)
     */
    @Override
    public void connected(String clientHostname, int clientPort) throws RemoteException {
        logger.info("Accounts:" + AppConstants.CONTEXT_PATH + " says - " + clientHostname + ":" + clientPort + " has connected.");
    }

    /**
     * @see smartshift.common.rmi.interfaces.BaseRemoteInterface#disconnecting(java.lang.String, int)
     */
    @Override
    public void disconnecting(String clientHostname, int clientPort) throws RemoteException {
        logger.info("Accounts:" + AppConstants.CONTEXT_PATH + " says - " + clientHostname + ":" + clientPort + " is disconnecting.");
    }
}
