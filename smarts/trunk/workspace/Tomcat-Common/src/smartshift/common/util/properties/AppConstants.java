package smartshift.common.util.properties;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

/**
 * Holds static properties based on App Properties
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class AppConstants {
    private static final Logger logger = Logger.getLogger(AppConstants.class);
    
    // NON-PROPERTY CONSTANTS
    
    /** The hostname that that application is running on */
    public static String HOSTNAME;

    /** The context path of this tomcat app */
    public static String CONTEXT_PATH;

    // PROPERTY-BASED STUFF
    
    /** The schema for the Accounts Database */
    public static String DB_ACCOUNTS_SCHEMA;

    /** The local rmi port */
    public static Integer RMI_LOCAL_PORT;

    /** The local rmi service name */
    public static String RMI_LOCAL_SERVICE_NAME;

    /** The rmi hostname for the accounts applications */
    public static String RMI_ACCOUNTS_HOSTNAME;
    
    /** The rmi port for the accounts applications */
    public static Integer RMI_ACCOUNTS_PORT;
    
    /** The rmi service name for the accounts applications */
    public static String RMI_ACCOUNTS_SERVICE_NAME;

    /**
     * Initialized static variables
     * 
     * @param servletContext to get tomcat infromation
     */
    public static void initialize(ServletContext servletContext) {
        // Not from properties
        try {
            HOSTNAME = InetAddress.getLocalHost().getHostName();
        } catch(UnknownHostException e) {
            logger.error("Failed to get hostname", e);
        }
        CONTEXT_PATH = servletContext.getContextPath().replaceAll("^/", "");

        // from properties
        DB_ACCOUNTS_SCHEMA = AppProperties.getProperty("database.schema.accounts");
        
        RMI_LOCAL_PORT = AppProperties.getIntegerProperty("rmi.port", -1);
        RMI_LOCAL_SERVICE_NAME = AppProperties.getProperty("rmi.serviceName");
        
        RMI_ACCOUNTS_HOSTNAME = AppProperties.getProperty("rmi.accounts.hostname");
        RMI_ACCOUNTS_PORT = AppProperties.getIntegerProperty("rmi.accounts.port", -1);
        RMI_ACCOUNTS_SERVICE_NAME = AppProperties.getProperty("rmi.accounts.serviceName");
        
        printValues();
    }
    
    private static void printValues() {
        try {
        for(Field field:AppConstants.class.getFields())
            if(Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()))
                logger.info(field.getName() + ":" + field.get(null));
        } catch(Exception e) {
            logger.warn("Failed to print constants", e);
        }
    }
}
