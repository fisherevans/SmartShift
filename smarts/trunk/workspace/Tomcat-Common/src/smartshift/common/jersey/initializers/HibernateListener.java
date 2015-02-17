package smartshift.common.jersey.initializers;

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
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("Closing the hibernate session factories");
        HibernateFactory.closeFactories();
        MultiTenantConnectionProviderImpl provider = MultiTenantConnectionProviderImpl.getInstance();
        if(provider != null)
            provider.close();
    }
}
