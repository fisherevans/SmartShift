package smartshift.common.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import smartshift.common.rmi.interfaces.BaseRemoteInterface;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * The implementation of the Base RMI interface that holds extra RMI connection info
 */
public abstract class BaseRemote extends UnicastRemoteObject implements BaseRemoteInterface {
    private static final long serialVersionUID = 38322224455859927L;

    protected BaseRemote() throws RemoteException {
        super();
    }

    /** The RMI Sever this remote is connected from */
    private String _rmiServerHostname;

    /** The RMI port this remote is connected from */
    private Integer _rmiServerPort;
            
    /** The RMI service name */
    private String _rmiServiceName;

    @Override
    public void setRMIInfo(String serverHostname, Integer serverPort, String serviceName) {
        _rmiServerHostname = serverHostname;
        _rmiServerPort = serverPort;
        _rmiServiceName = serviceName;
    }

    @Override
    public String getRMIServerHostname() {
        return _rmiServerHostname;
    }

    @Override
    public Integer getRMIServerPort() {
        return _rmiServerPort;
    }

    @Override
    public String getRMIServiceName() {
        return _rmiServiceName;
    }

    @Override
    public String getRMIInfo() throws RemoteException {
        return getRMIServerHostname() + ":" + getRMIServerPort() + "/" + getRMIServiceName();
    }

    @Override
    public String ping() throws RemoteException {
        return "pong";
    }
}
