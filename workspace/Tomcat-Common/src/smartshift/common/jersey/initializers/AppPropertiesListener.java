package smartshift.common.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.core.QuartzScheduler;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;
import smartshift.common.util.properties.AppProperties;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          the listener for app properties
 */
public class AppPropertiesListener implements ServletContextListener {  
    private static final SmartLogger logger = new SmartLogger(AppPropertiesListener.class);
    
    private static final String LOG_TO_FILE = "log4j-file.properties";
    private static final String LOG_TO_CONSOLE = "log4j-console.properties";

    /**
     * a context has been initialized, load app properties
     */
	@Override
    public void contextInitialized(ServletContextEvent event) {
        AppProperties.loadProperties(event.getServletContext());

        if(AppConstants.LOG_TO_FILE)
            PropertyConfigurator.configure(AppPropertiesListener.class.getClassLoader().getResourceAsStream(LOG_TO_FILE));
        else
            PropertyConfigurator.configure(AppPropertiesListener.class.getClassLoader().getResourceAsStream(LOG_TO_CONSOLE));
        
        LogManager.getLogger("com.mchange.v2").setLevel(Level.WARN);
        LogManager.getLogger(QuartzScheduler.class).setLevel(Level.WARN);
        
        logger.info("Log4J configuration has been loaded (To file: " + AppConstants.LOG_TO_FILE + ")");
        
        AppConstants.logValues();
    }  

    /**
     * a context has been destroyed
     */
	@Override
    public void contextDestroyed(ServletContextEvent event) {
        MDC.clear();
    }
}
