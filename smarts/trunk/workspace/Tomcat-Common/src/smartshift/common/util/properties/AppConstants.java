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

    /** The auth type to use: user/session */
    public static String AUTH_TYPE;
    
    /** The hostname (including port) of db server */
    public static String DB_SERVER_HOSTNAME;
    
    /** The default schema when asking for a session */
    public static String DB_SCHEMA_DEFAULT;
    
    /** The initial set of schemas to connect to */
    public static String[] DB_SCHEMA_INITIAL_SET;
    
    /** The schema for the Accounts Database */
    public static String DB_SCHEMA_ACCOUNTS;
    
    /** The schema for the local Business Database */
    public static String DB_SCHEMA_BUSINESS_BASE;
    
    
    
    /** The rmi port for the business applications */
    public static Integer RMI_BUSINESS_PORT;

    /** The accounts rmi port */
    public static Integer RMI_ACCOUNTS_PORT;

    /** The business rmi service name */
    public static String RMI_BUSINESS_SERVICE_NAME;

    /** The accounts rmi service name */
    public static String RMI_ACCOUNTS_SERVICE_NAME;

    /** The rmi hostname for the accounts applications */
    public static String RMI_ACCOUNTS_HOSTNAME;
    
    
    
    /** Time in ms that sessions expire in */
    public static long SESSION_TIMEOUT;

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
        AUTH_TYPE = AppProperties.getProperty("app.authType");
        
        DB_SERVER_HOSTNAME = AppProperties.getProperty("database.server.hostname");
        DB_SCHEMA_DEFAULT = AppProperties.getProperty("database.schema.default");
        DB_SCHEMA_ACCOUNTS = AppProperties.getProperty("database.schema.accounts");
        DB_SCHEMA_BUSINESS_BASE = AppProperties.getProperty("database.schema.business.base");
        DB_SCHEMA_INITIAL_SET = AppProperties.getProperty("database.schema.initialSet").split(",");

        RMI_BUSINESS_PORT = AppProperties.getIntegerProperty("rmi.business.port", -1);
        RMI_ACCOUNTS_PORT = AppProperties.getIntegerProperty("rmi.accounts.port", -1);
        RMI_BUSINESS_SERVICE_NAME = AppProperties.getProperty("rmi.business.serviceName");
        RMI_ACCOUNTS_SERVICE_NAME = AppProperties.getProperty("rmi.accounts.serviceName");
        
        RMI_ACCOUNTS_HOSTNAME = AppProperties.getProperty("rmi.accounts.hostname");
        
        SESSION_TIMEOUT = AppProperties.getLongProperty("sesson.timeout", 15L*60L*1000L); // default to 15 minutes
        
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
