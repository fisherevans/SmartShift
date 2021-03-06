package smartshift.common.util.properties;

import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Static properties system. The properties are based on a java properties file that is loaded at runtime
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class AppProperties {
    private static final String PROPERTIES_FILE = "app.properties";

    private static final SmartLogger logger = new SmartLogger(AppProperties.class);

    /**
     * The properties map
     */
    private static Properties properties = new Properties();

    /**
     * Loads the properties from the default properties location statically
     * declared as PROPERTIES_FILE
     * @param servletContext for tomcat info
     */
    public static void loadProperties(ServletContext servletContext) {
        synchronized(properties) {
            String context = servletContext.getContextPath().replaceAll("^/", "");
            properties = new Properties();
            loadFromFile(PROPERTIES_FILE, properties);
            loadFromFile("global.properties", properties);
            loadFromFile(context + ".properties", properties);
            AppConstants.initialize(servletContext);
        }
    }
    
    private static void loadFromFile(String filePath, Properties baseProperties) {
        try {
            Properties properties = new Properties();
            InputStream fileInput = AppProperties.class.getClassLoader().getResourceAsStream(filePath);
            properties.load(fileInput);
            baseProperties.putAll(properties);
        } catch(NullPointerException e) {
            logger.warn("Failed to load properties because file could not be read: " + filePath);
        } catch(Exception e) {
            logger.error("Failed to load properties from: " + filePath);
        }
    }
    
    /**
     * Checks if a property is set
     * @param key the key to check
     * @return true if the key exists
     */
    public static boolean exists(String key) {
        return properties.containsKey(key);
    }

    /**
     * Gets the value of a property
     * 
     * @see java.util.Properties#getProperty(String)
     * @param key The key of the property to get
     * @return the value of the property. Null if the property is not set.
     */
    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    /**
     * Gets the value of a property
     * 
     * @see java.util.Properties#getProperty(String, String)
     * @param key the key of the property to get
     * @param defaultValue If the property is not set, return this value
     * @return The set property, or the default if not found
     */
    public static String getProperty(String key, String defaultValue) {
        if(properties == null)
            return defaultValue;
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Gets the Integer value of a property
     * 
     * @see java.util.Properties#getProperty(String)
     * @param key The key of the property to get
     * @return the Integer value of the property. Null if the property is not set.
     */
    public static Integer getIntegerProperty(String key) {
        return getIntegerProperty(key, null);
    }

    /**
     * Gets the Integer value of a property
     * 
     * @see java.util.Properties#getProperty(String, String)
     * @param key the key of the property to get
     * @param defaultValue If the property is not set, return this value
     * @return The Integer vale set property, or the default if not found
     */
    public static Integer getIntegerProperty(String key, Integer defaultValue) {
        if(properties == null)
            return defaultValue;
        String prop = properties.getProperty(key, defaultValue + "");
        if(prop == null)
            return defaultValue;
        try {
            return new Integer(prop);
        } catch(Exception e) {
            logger.warn("Failed to fetch " + key + " as Integer");
            return defaultValue;
        }
    }

    /**
     * Gets the Double value of a property
     * 
     * @see java.util.Properties#getProperty(String)
     * @param key The key of the property to get
     * @return the Double value of the property. Null if the property is not set.
     */
    public static Double getDoubleProperty(String key) {
        return getDoubleProperty(key, null);
    }

    /**
     * Gets the Double value of a property
     * 
     * @see java.util.Properties#getProperty(String, String)
     * @param key the key of the property to get
     * @param defaultValue If the property is not set, return this value
     * @return The Double vale set property, or the default if not found
     */
    public static Double getDoubleProperty(String key, Double defaultValue) {
        if(properties == null)
            return defaultValue;
        String prop = properties.getProperty(key, defaultValue.toString());
        if(prop == null)
            return defaultValue;
        try {
            return new Double(prop);
        } catch(Exception e) {
            logger.warn("Failed to fetch " + key + " as Double");
            return defaultValue;
        }
    }

    /**
     * Gets the Boolean value of a property
     * 
     * @see java.util.Properties#getProperty(String)
     * @param key The key of the property to get
     * @return the Boolean value of the property. Null if the property is not set.
     */
    public static Boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, null);
    }

    /**
     * Gets the Boolean value of a property
     * 
     * @see java.util.Properties#getProperty(String, String)
     * @param key the key of the property to get
     * @param defaultValue If the property is not set, return this value
     * @return The Boolean vale set property, or the default if not found
     */
    public static Boolean getBooleanProperty(String key, Boolean defaultValue) {
        if(properties == null)
            return defaultValue;
        String prop = properties.getProperty(key, defaultValue.toString());
        if(prop == null)
            return defaultValue;
        try {
            return new Boolean(prop);
        } catch(Exception e) {
            logger.warn("Failed to fetch " + key + " as Boolean");
            return defaultValue;
        }
    }

    /**
     * Gets the Long value of a property
     * 
     * @see java.util.Properties#getProperty(String)
     * @param key The key of the property to get
     * @return the Long value of the property. Null if the property is not set.
     */
    public static Long getLongProperty(String key) {
        return getLongProperty(key, null);
    }

    /**
     * Gets the Long value of a property
     * 
     * @see java.util.Properties#getProperty(String, String)
     * @param key the key of the property to get
     * @param defaultValue If the property is not set, return this value
     * @return The Long vale set property, or the default if not found
     */
    public static Long getLongProperty(String key, Long defaultValue) {
        if(properties == null)
            return defaultValue;
        String prop = properties.getProperty(key, defaultValue.toString());
        if(prop == null)
            return defaultValue;
        try {
            return new Long(prop);
        } catch(Exception e) {
            logger.warn("Failed to fetch " + key + " as Long");
            return defaultValue;
        }
    }
}
