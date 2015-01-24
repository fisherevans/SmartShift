package smartshift.common.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import smartshift.common.hibernate.HibernateFactory;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          the listener for hibernate
 */
public class HibernateListener implements ServletContextListener {  
    private static final Logger logger = Logger.getLogger(HibernateListener.class);

    /**
     * a context has been initialized, init hibernate factories
     */
	@Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("Initializing he hibernate session factory");
        HibernateFactory.initialize();
    }  

    /**
     * a context has been destroyed, close hibernate factories
     */
	@Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("Closing the hibernate session factories");
        HibernateFactory.closeFactories();
        
        /*
        // De-register old class loaders
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("Deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.fatal(String.format("Error deregistering driver %s", driver), e);
            }
        }
        */
    }
}
