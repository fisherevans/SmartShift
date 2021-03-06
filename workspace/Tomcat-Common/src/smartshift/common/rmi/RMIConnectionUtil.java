package smartshift.common.rmi;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import smartshift.common.rmi.interfaces.BaseRemoteInterface;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Util class for RMI connections
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class RMIConnectionUtil {
    private static final SmartLogger logger = new SmartLogger(RMIConnectionUtil.class);
    
    /**
     * attempt to configure the connection if it doesn't exist
     * @param host the registry hostname
     * @param port the registry port
     * @param serviceName the registry service name
     * @return true if the connection is established
     */
    public static boolean pollConnection(String host, int port, String serviceName) {
        String nickname = host + ":" + port + "/" + serviceName;
        if(!RMIClient.isClinetStarted(host, port)) {
            try {
                logger.info("Initializing " + nickname + " RMI client");
                RMIClient.startClient(host, port);
            } catch(RemoteException e) {
                logger.warn("Failed to start " + nickname + " client", e);
                return false;
            }
        }
        
        if(!RMIClient.isServiceConnected(host, port, serviceName)) {
            try {
                logger.info("Connecting the " + nickname + " RMI service");
                RMIClient.connectService(host, port, serviceName);
            } catch(ConnectException e) {
                logger.warn(nickname + " server is unreachable - failed to connect to: " + host + ":" + port);
                return false;
            } catch(MalformedURLException | RemoteException | NotBoundException e) {
                logger.warn("Failed to connect the " + nickname + " service", e);
                return false;
            }
        }
        
        try {
            BaseRemoteInterface service = (BaseRemoteInterface) RMIClient.getService(host, port, serviceName);
            try {
                if(!"pong".equals(service.ping())) {
                    logger.warn(nickname + "PING DOES NOT PONG, NOT GOOD");
                    return false;
                }
            } catch(ConnectException e) {
                logger.warn("Failed to ping-pong. " + nickname + " server is unreachable - failed to connect to: " + host + ":" + port);
                return false;
            }  catch(RemoteException e) {
                logger.warn("Failed to call ping-pong method in " + nickname + " service", e);
                return false;
            }
        } catch(ClassCastException e) {
            logger.warn(nickname + " service is an invalid class", e);
            return false;
        }
        return true;
    }
}
