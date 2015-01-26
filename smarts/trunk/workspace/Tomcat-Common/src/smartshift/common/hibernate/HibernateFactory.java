package smartshift.common.hibernate;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import smartshift.common.util.PrimativeUtils;
import smartshift.common.util.properties.AppConstants;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A hibernate factory class, used to access the database using objects
 *          and annotation
 */
public class HibernateFactory {
    private static Logger logger = Logger.getLogger(HibernateFactory.class);
	
	/**
	 * The map of hibernate factories <schema, session facory>
	 */
	private final static Map<String, SessionFactory> factories = new HashMap<>();
	
	/**
	 * Initialized the DB factories based on the tables found in the app properties
	 */
	public synchronized static void initialize() {
        logger.info("Initializing - Auto-Connecting to: " + PrimativeUtils.joinArray(AppConstants.DB_SCHEMA_INITIAL_SET, ", "));
        for(String schema:AppConstants.DB_SCHEMA_INITIAL_SET)
            createFactory(schema);
	}
	
	/**
	 * Creates a DB factory based on a schema
	 * @param schema the schema to connect to
	 */
	public synchronized static void createFactory(String schema) {
	    logger.info("Connection to schema: " + schema);
	    Configuration cfg = MultiTenantConnectionProviderImpl.getConnectionConfiguration(schema);
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
        SessionFactory factory = cfg.buildSessionFactory(ssrb.build());
        factories.put(schema, factory);
	}
	
    /**
     * @param schema
     * @return the hibernate factory for the given schema
     */
	public synchronized static SessionFactory getFactory(String schema) {
		return factories.get(schema);
	}
	
    /**
     * @param schema
     * @return a hibernate db session
     */
	public synchronized static Session getSession(String schema) {
		SessionFactory factory = getFactory(schema);
		if(factory == null) {
			logger.info("Failed to retrieve Hibernate Session for DB: " + schema);
			return null;
		}
		return factory.withOptions().tenantIdentifier(schema).openSession();
	}
	
    /**
     * close all hibernate factories
     */
	public synchronized static void closeFactories() {
        logger.info("Closing all hibernate session factories.");
		for(SessionFactory factory:factories.values()) {
			if(factory != null) {
				factory.close();
			}
		}
		factories.clear();
	}
}
