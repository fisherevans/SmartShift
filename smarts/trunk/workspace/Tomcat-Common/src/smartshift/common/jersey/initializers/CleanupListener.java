package smartshift.common.jersey.initializers;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          the listener for hibernate
 */
public class CleanupListener implements ServletContextListener {  
    private static final SmartLogger logger = new SmartLogger(CleanupListener.class);

    /**
     * a context has been initialized
     */
	@Override
    public void contextInitialized(ServletContextEvent event) {
    }  

    /**
     * a context has been destroyed
     */
	@SuppressWarnings("deprecation")
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // De-register old class loaders
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("Deregistered jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.fatal(String.format("Error deregistering driver %s", driver), e);
            }
        }
        
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for(Thread t:threadArray) {
            if(t.getName().contains("cleanup") || t.getName().contains("mchange.v2") || t.getName().contains("Timer")) {
                synchronized(t) {
                    logger.info("Killing thread: " + t.getName() + " " + t.getId());
                    t.stop(); //don't complain, it works
                }
            }
        }
    }
}
