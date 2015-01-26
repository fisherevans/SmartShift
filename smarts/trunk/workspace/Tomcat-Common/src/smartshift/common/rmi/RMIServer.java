package smartshift.common.rmi;

import java.io.InvalidClassException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * Hosts RMI services on its own registry
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class RMIServer {
    private static final SmartLogger logger = new SmartLogger(RMIServer.class);
    
    private static Map<String, BaseRemote> _services = new HashMap<>();
    private static Registry _registry = null;
    private static int _port;
    
    /**
     * Starts a service registry
     * @param port the port to start the registry
     * @throws RemoteException if an error occurs
     */
    public synchronized static void start(int port) throws RemoteException {
        if(_registry != null)
            destroyAll();
        _registry = LocateRegistry.createRegistry(port);
        logger.info("RMI Server started on port: " + port);
        _port = port;
    }
    
    /**
     * Creates a service - requires a registry to be started
     * @param clazz the service class
     * @param serviceName the service name to host
     * @throws InvalidClassException 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws AlreadyBoundException
     */
    @SuppressWarnings("rawtypes")
    public synchronized static void create(Class clazz, String serviceName) throws InvalidClassException, InstantiationException, IllegalAccessException, RemoteException, MalformedURLException, AlreadyBoundException {
        if(serviceName == null || serviceName.trim().length() == 0)
            throw new IllegalArgumentException("Invalid service name");
        if(_registry == null)
            throw new IllegalStateException("You must start the RMI registry first: " + serviceName);
        if(_services.get(serviceName) != null)
            throw new IllegalStateException("You must destroy the RMI server before createing a new one: " + serviceName);
        if(!BaseRemote.class.isAssignableFrom(clazz))
            throw new InvalidClassException(clazz.toString() + " does not extend " + Remote.class.toString());
        BaseRemote service = (BaseRemote) clazz.newInstance();
        _registry.bind(serviceName, service);
        service.setRMIInfo(AppConstants.HOSTNAME, _port, serviceName);
        _services.put(serviceName, service);
        logger.info("RMI Service: '" + serviceName + "' bound via '" + clazz.getName() + "'");
    }
    
    /**
     * destroy all services to cleanly shut down
     * @throws RemoteException
     */
    public synchronized static void destroyAll() throws RemoteException {
        List<String> serviceNames = new ArrayList<>(_services.size());
        serviceNames.addAll(_services.keySet());
        for(String serviceName:serviceNames) {
            try {
                destroy(serviceName);
            } catch(MalformedURLException | NotBoundException e) {
                logger.error("Cached service is invalid", e);
            }
        }
    }
    
    /**
     * destroy a specific service name
     * @param serviceName the service to destroy
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws NotBoundException
     */
    public synchronized static void destroy(String serviceName) throws RemoteException, MalformedURLException, NotBoundException {
        logger.info("Destroying RMI Service: " + serviceName);
        Remote service = _services.get(serviceName);
        if(service != null) {
            _registry.unbind(serviceName);
            _services.remove(serviceName);
        }
    }
}
