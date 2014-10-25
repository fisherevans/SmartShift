package smartshift.common.util.properties;

import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class AppProperties {
    private static final String PROPERTIES_FILE = "res/app.properties";

    private static final Logger logger = Logger.getLogger(AppProperties.class);

    private static Properties properties = new Properties();

    /**
     * Loads the properties from the default properties location statically
     * declared as PROPERTIES_FILE
     */
    public static void loadProperties() {
        synchronized(properties) {
            try {
                Properties newProperties = new Properties();
                InputStream fileInput = AppProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
                newProperties.load(fileInput);
                properties = newProperties;
            } catch(Exception e) {
                logger.error("Failed to load properties from: " + PROPERTIES_FILE, e);
            }
        }
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
            return null;
        return properties.getProperty(key, defaultValue);
    }
}
