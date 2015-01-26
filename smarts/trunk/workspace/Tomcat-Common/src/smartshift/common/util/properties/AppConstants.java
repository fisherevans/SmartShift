package smartshift.common.util.properties;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.ServletContext;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Holds static properties based on App Properties
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class AppConstants {
    private static final SmartLogger logger = new SmartLogger(AppConstants.class);
    
    // NON-PROPERTY CONSTANTS
    
    /** The hostname that that application is running on */
    public static String HOSTNAME;

    /** The context path of this tomcat app */
    public static String CONTEXT_PATH;

    /** Whether this is a dev buil or not */
    public static Boolean DEV_BUILD;
    
    /** Whether or not to log to file or stdout */
    public static Boolean LOG_TO_FILE;

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
    
    /** businesses to conenct to and post to accounts via rmi on connection, used for debugging and dev instances */
    public static Integer[] DEV_BUSINESS_MANUAL_BUSINESSES;

    /**
     * Initialized static variables
     * 
     * @param servletContext to get tomcat infromation
     */
    public static void initialize(ServletContext servletContext) {
        // Not from properties
        try {
            HOSTNAME = AppProperties.getProperty("app.hostnameOverride");
            if(HOSTNAME == null || HOSTNAME.trim().length() == 0)
                HOSTNAME = InetAddress.getLocalHost().getHostName();
        } catch(UnknownHostException e) {
            logger.error("Failed to get hostname", e);
        }
        CONTEXT_PATH = servletContext.getContextPath().replaceAll("^/", "");

        // from properties
        DEV_BUILD = AppProperties.getBooleanProperty("app.developmentApp", false);
        AUTH_TYPE = AppProperties.getProperty("app.authType", "user");
        LOG_TO_FILE = AppProperties.getBooleanProperty("app.log.toFile", true);
        
        DB_SERVER_HOSTNAME = AppProperties.getProperty("database.server.hostname");
        DB_SCHEMA_DEFAULT = AppProperties.getProperty("database.schema.default");
        DB_SCHEMA_ACCOUNTS = AppProperties.getProperty("database.schema.accounts");
        DB_SCHEMA_BUSINESS_BASE = AppProperties.getProperty("database.schema.business.base");
        if(AppProperties.getProperty("database.schema.initialSet") != null)
            DB_SCHEMA_INITIAL_SET = AppProperties.getProperty("database.schema.initialSet").split(",");

        RMI_BUSINESS_PORT = AppProperties.getIntegerProperty("rmi.business.port", -1);
        RMI_ACCOUNTS_PORT = AppProperties.getIntegerProperty("rmi.accounts.port", -1);
        RMI_BUSINESS_SERVICE_NAME = AppProperties.getProperty("rmi.business.serviceName");
        RMI_ACCOUNTS_SERVICE_NAME = AppProperties.getProperty("rmi.accounts.serviceName");
        
        RMI_ACCOUNTS_HOSTNAME = AppProperties.getProperty("rmi.accounts.hostname");
        
        SESSION_TIMEOUT = AppProperties.getLongProperty("sesson.timeout", 15L*60L*1000L); // default to 15 minutes
        
        try {
            DEV_BUSINESS_MANUAL_BUSINESSES = new Integer[0];
            String tempStr = AppProperties.getProperty("dev.business.manualBusinesses", "");
            if(tempStr.length() > 0) {
                String[] tempStrArr = tempStr.split(",");
                Integer[] tempIntArr = new Integer[tempStrArr.length];
                for(int id = 0;id < tempIntArr.length;id++)
                    tempIntArr[id] = new Integer(tempStrArr[id]);
                DEV_BUSINESS_MANUAL_BUSINESSES = tempIntArr;
            }
        } catch(Exception e) {
            logger.error("Failed to parse dev.business.manualBusinesses");
        }
    }
    
    /**
     * logs all static fields to the logger
     */
    public static void logValues() {
        try {
        for(Field field:AppConstants.class.getFields())
            if(Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
                String value;
                if(field.getType().isArray()) {
                    value = "";
                    for(Object ele:(Object[])field.get(null))
                        value += ele.toString() + " ";
                } else
                    value = field.get(null).toString();
                logger.info(field.getName() + ":" + value);
            }
        } catch(Exception e) {
            logger.warn("Failed to print constants", e);
        }
    }
}
