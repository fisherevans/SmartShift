package smartshift.common.jersey.initializers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import smartshift.common.util.json.GsonFactory;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          the listener for gson factory
 */
public class GsonFactoryListener implements ServletContextListener {  
    /**
     * a context has been initialized, init gson factory
     */
	@Override
    public void contextInitialized(ServletContextEvent event) {
        GsonFactory.initialize();
    }  

    /**
     * a context has been destroyed
     */
	@Override
    public void contextDestroyed(ServletContextEvent event) {

    }
}
