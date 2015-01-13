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
}