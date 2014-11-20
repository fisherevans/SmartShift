package smartshift.common.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.common.util.properties.AppProperties;
import smartshift.common.util.properties.StaticProperties;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          the listener for app properties
 */
public class AppPropertiesListener implements ServletContextListener {  

    /**
     * a context has been initialized, load app properties
     */
	@Override
    public void contextInitialized(ServletContextEvent event) {   
        AppProperties.loadProperties();
        StaticProperties.initialize();
    }  

    /**
     * a context has been destroyed
     */
	@Override
    public void contextDestroyed(ServletContextEvent event) {
        // Do Nothing
    }
}
