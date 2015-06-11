package smartshift.common.rmi;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import smartshift.common.rmi.interfaces.AccountsServiceInterface;
import smartshift.common.rmi.interfaces.BaseRemoteInterface;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 *  Class for connecting, maintaining and disconnecting remote services
 */
public class RMIClient {
    private static SmartLogger logger = new SmartLogger(RMIClient.class);

    private static Map<ServerTouple, RegistryServices> _serversRegistries = new HashMap<>();

    /**
     * Starts a client for a service registry
     * @param serverHostname the hostname of the service registry
     * @param port the port of the service registry
     * @throws RemoteException
     */
    public synchronized static void startClient(String serverHostname, int port) throws RemoteException {
        ServerTouple server = new ServerTouple(serverHostname, port);
        if(_serversRegistries.get(server) != null)
            throw new IllegalStateException("RMI Client already started for " + server.toString());
        Registry registry = LocateRegistry.getRegistry(server.hostname, server.port);
        _serversRegistries.put(server, new RegistryServices(registry)); 
        logger.info("RMI Client started: " + server);
    }

    /**
     * stops a connection to the service registry
     * @param serverHostname the registry hostname
     * @param port the registry port
     */
    public synchronized static void stopClient(String serverHostname, int port) {
        ServerTouple server = new ServerTouple(serverHostname, port);
        if(_serversRegistries.get(server) != null) {
            _serversRegistries.remove(server);
            logger.info("RMI Client stopped: " + server.hostname + ":" + server.port);
        } else
            logger.info("RMI Client already stopped: " + server);
    }
    
    /**
     * checks if there is an open connection to a registry
     * @param serverHostname the registry hostname
     * @param port the port of the registry
     * @return true if there is a connection
     */
    public synchronized static boolean isClinetStarted(String serverHostname, int port) {
        ServerTouple server = new ServerTouple(serverHostname, port);
        return _serversRegistries.get(server) != null;
    }
    
    /**
     * Connect to a service
     * @param serverHostname the registry hostname
     * @param port the reistry port
     * @param serviceName the service name
     * @return the service interface
     * @throws MalformedURLException invalid registry url
     * @throws RemoteException if there was an error initializing the remote service
     * @throws NotBoundException if the registry i invalid
     * @throws ConnectException if there is a network error
     */
    public static BaseRemoteInterface connectService(String serverHostname, int port, String serviceName) throws MalformedURLException, RemoteException, NotBoundException, ConnectException {
        RegistryServices registryServices = getRegistryServices(serverHostname, port);
        if(registryServices == null)
            throw new IllegalStateException("RMI Client not yet started for " + serverHostname + ":" + port);
        BaseRemoteInterface service = registryServices.services.get(serviceName);
        if(service != null)
            throw new IllegalStateException("RMI Client already conntected to " + serverHostname + ":" + port + "/" + serviceName);
        service = (BaseRemoteInterface) registryServices.registry.lookup(serviceName);
        registryServices.services.put(serviceName, service);
        //service.connected(AppConstants.HOSTNAME, AppConstants.RMI_LOCAL_PORT);
        logger.info("RMI Service connectected to: " + serverHostname + ":" + port + "/" + serviceName);
        return service;
    }
    
    /**
     * disconnect from a service 
     * @param serverHostname the registry server hostname
     * @param port the registry port
     * @param serviceName the service name
     * @throws RemoteException if there is an error disconnecting
     */
    public static void disconnectService(String serverHostname, int port, String serviceName) throws RemoteException {
        RegistryServices registryServices = getRegistryServices(serverHostname, port);
        //BaseRemoteInterface service = registryServices.services.get(serviceName);
        //service.disconnecting(AppConstants.HOSTNAME, AppConstants.RMI_LOCAL_PORT);
        registryServices.services.remove(serviceName);
        logger.info("RMI Service disconnectected from: " + serverHostname + ":" + port + "/" + serviceName);
    }
    
    /**
     * get a connected service
     * @param serverHostname the registry hostname
     * @param port the registry port
     * @param serviceName the service name
     * @return the service interface
     */
    public static BaseRemoteInterface getService(String serverHostname, int port, String serviceName) {
        RegistryServices registryServices = getRegistryServices(serverHostname, port);
        if(registryServices == null)
            throw new IllegalStateException("RMI Client not yet started for " + serverHostname + ":" + port);
        BaseRemoteInterface service = registryServices.services.get(serviceName);
        return service;
    }
    
    /** calls getService() using the AppConstants.RMI_ACCOUNTS_INFO
     * @return the accounts service
     */
    public static AccountsServiceInterface getAccountsService() {
        try {
            AccountsServiceInterface accountsService = (AccountsServiceInterface) getService(
                    AppConstants.RMI_ACCOUNTS_HOSTNAME,AppConstants.RMI_ACCOUNTS_PORT, AppConstants.RMI_ACCOUNTS_SERVICE_NAME);
            return accountsService;
        } catch(Exception e) {
            logger.error("Failed to fetch accounts service", e);
            return null;
        }
    }
    
    /**
     * checks if service is connected
     * @param serverHostname the registry server hostname
     * @param port the registry port
     * @param serviceName the service name
     * @return true is connection is present
     */
    public static boolean isServiceConnected(String serverHostname, int port, String serviceName) {
        RegistryServices registryServices = getRegistryServices(serverHostname, port);
        if(registryServices == null)
            throw new IllegalStateException("RMI Client not yet started for " + serverHostname + ":" + port);
        BaseRemoteInterface service = registryServices.services.get(serviceName);
        return service != null;
    }
    
    /**
     * get mapped registry services
     * @param serverHostname
     * @param port
     * @return
     */
    private static RegistryServices getRegistryServices(String serverHostname, int port) {
        ServerTouple server = new ServerTouple(serverHostname, port);
        RegistryServices registryServices = _serversRegistries.get(server);
        return registryServices;
    }
    
    /**
     * Simple touple to hold a server and port
     * @author D. Fisher Evans <contact@fisherevans.com>
     *
     */
    private static class ServerTouple {
        public String hostname;
        public Integer port;
        
        public ServerTouple(String hostname, int port) {
            if(hostname == null)
                throw new IllegalArgumentException("Hostname is null!");
            this.hostname = hostname;
            this.port = port;
        }

        @Override
        public int hashCode() {
            return hostname.hashCode() * (port.hashCode() % 104161);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof ServerTouple) {
                ServerTouple other = (ServerTouple) obj;
                return hostname.equalsIgnoreCase(other.hostname) && port.equals(other.port);
            } else
                return false;
        }

        @Override
        public String toString() {
            return hostname + ":" + port;
        }
    }
    
    /**
     * A map for a registry of service names to interfaces
     * @author D. Fisher Evans <contact@fisherevans.com>
     *
     */
    private static class RegistryServices {
        public Registry registry;
        public Map<String, BaseRemoteInterface> services = new HashMap<>();
        
        public RegistryServices(Registry registry) {
            this.registry = registry;
        }
    }
}
