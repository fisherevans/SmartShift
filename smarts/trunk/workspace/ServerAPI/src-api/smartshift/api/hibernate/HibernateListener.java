package smartshift.api.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          the listener for hibernate
 */
public class HibernateListener implements ServletContextListener {  

    /**
     * a context has been initialized, init hibernate factories
     */
	@Override
    public void contextInitialized(ServletContextEvent event) {  
        HibernateFactory.createFactories();      
    }  

    /**
     * a context has been destroyed, close hibernate factories
     */
	@Override
    public void contextDestroyed(ServletContextEvent event) {  
        HibernateFactory.closeFactories();
    }
}
