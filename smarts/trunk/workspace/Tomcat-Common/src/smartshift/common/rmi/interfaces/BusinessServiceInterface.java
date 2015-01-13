package smartshift.common.rmi.interfaces;

import java.rmi.RemoteException;

/**
 * The business RMI service
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public interface BusinessServiceInterface extends BaseRemoteInterface {
    /**
     * A test method
     * @return some number
     * @throws RemoteException
     */
    public double getCost() throws RemoteException;
}
