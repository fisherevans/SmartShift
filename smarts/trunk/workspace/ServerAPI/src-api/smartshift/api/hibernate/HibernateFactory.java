package smartshift.api.hibernate;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A hibernate factory class, used to access the database using objects
 *          and annotation
 */
public class HibernateFactory {
    /** the logger */
	public static Logger logger = Logger.getLogger(HibernateFactory.class);
	
    /** the database's url */
	public static final String BASE_URL = "jdbc:mysql://localhost/";
	
    /** an array of db names */
	public static final String[] DATABASES = new String[] { "smartshift" };
	
	private final static Map<String, SessionFactory> factories = new HashMap<>();
	
    /**
     * set up the hibernate connection
     */
	public synchronized static void createFactories() {
		closeFactories();
		Configuration baseConfig =  new Configuration().configure();
		for(String database:DATABASES) {
			baseConfig.getProperties().setProperty("hibernate.connection.url", BASE_URL + database);
			baseConfig.configure();
			SessionFactory factory = baseConfig.buildSessionFactory();
			factories.put(database, factory);
		}
	}
	
    /**
     * @param database
     * @return the hibernate factory for the given database
     */
	public synchronized static SessionFactory getFactory(String database) {
		return factories.get(database);
	}
	
    /**
     * @param database
     * @return a hibernate db session
     */
	public synchronized static Session getSession(String database) {
		SessionFactory factory = getFactory(database);
		if(factory == null) {
			logger.info("Failed to retrieve Hibernate Session for DB: " + database);
			return null;
		}
		return factory.openSession();
	}
	
    /**
     * close all hibernate factories
     */
	public synchronized static void closeFactories() {
		for(SessionFactory factory:factories.values()) {
			if(factory != null) {
				factory.close();
			}
		}
		factories.clear();
	}
}
