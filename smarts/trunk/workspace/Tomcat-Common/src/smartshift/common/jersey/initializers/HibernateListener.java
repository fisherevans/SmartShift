package smartshift.common.jersey.initializers;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.MultiTenantConnectionProviderImpl;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          the listener for hibernate
 */
public class HibernateListener implements ServletContextListener {  
    private static final SmartLogger logger = new SmartLogger(HibernateListener.class);

    /**
     * a context has been initialized, init hibernate factories
     */
	@Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("Initializing he hibernate session factory");
        //System.setProperty("com.mchange.v2.c3p0.management.ManagementCoordinator", "com.mchange.v2.c3p0.management.NullManagementCoordinator");
        HibernateFactory.initialize();
        for(Integer businessID:AppConstants.DEV_BUSINESS_MANUAL_BUSINESSES)
            HibernateFactory.createFactory(AppConstants.DB_SCHEMA_BUSINESS_BASE + businessID);
    }  

    /**
     * a context has been destroyed, close hibernate factories
     */
	@SuppressWarnings("deprecation")
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("Closing the hibernate session factories");
        HibernateFactory.closeFactories();
        MultiTenantConnectionProviderImpl provider = MultiTenantConnectionProviderImpl.getInstance();
        if(provider != null)
            provider.close();

        // De-register old class loaders
//        Enumeration<Driver> drivers = DriverManager.getDrivers();
//        while (drivers.hasMoreElements()) {
//            Driver driver = drivers.nextElement();
//            try {
//                DriverManager.deregisterDriver(driver);
//                logger.info(String.format("Deregistering jdbc driver: %s", driver));
//            } catch (SQLException e) {
//                logger.fatal(String.format("Error deregistering driver %s", driver), e);
//            }
//        }
        
//        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
//        for(Thread t:threadArray) {
//            if(t.getName().contains("Abandoned connection cleanup thread")) {
//                synchronized(t) {
//                    t.stop(); //don't complain, it works
//                }
//            }
//        }
    }
}
