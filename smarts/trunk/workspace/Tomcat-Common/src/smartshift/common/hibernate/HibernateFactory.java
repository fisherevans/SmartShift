package smartshift.common.hibernate;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import smartshift.common.util.properties.AppProperties;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A hibernate factory class, used to access the database using objects
 *          and annotation
 */
public class HibernateFactory {
    @SuppressWarnings("javadoc")
    public static Logger logger = Logger.getLogger(HibernateFactory.class);
    
    /**
     * The property key for the db server
     */
    public static final String DB_URL_PROP = "database.server";

    /**
     * The property key for the schema list
     */
    public static final String DB_SCHEMAS_PROP = "database.schemas";
    
    /**
     * The delimiter separating schema's
     */
    public static final String DB_SCHEMAS_DELIM = ",";
	
	/**
	 * The map of hibernate factories <schema, session facory>
	 */
	private final static Map<String, SessionFactory> factories = new HashMap<>();
	
    /**
     * set up the hibernate connection
     */
	public synchronized static void createFactories() {
	    if(!AppProperties.exists(DB_URL_PROP) || AppProperties.exists(DB_SCHEMAS_PROP))
	        logger.fatal("Database properties are not set!");
        String baseUrl = "jdbc:mysql://" + AppProperties.getProperty(DB_URL_PROP) + "/";
        String[] schemas = AppProperties.getProperty(DB_SCHEMAS_PROP).split(DB_SCHEMAS_DELIM);
		closeFactories();
		Configuration baseConfig =  new Configuration().configure();
		StandardServiceRegistryBuilder ssrb;
		for(String schema:schemas) {
            baseConfig.getProperties().setProperty("hibernate.connection.url", baseUrl + schema);
            ssrb = new StandardServiceRegistryBuilder().applySettings(baseConfig.getProperties());
			SessionFactory factory = baseConfig.buildSessionFactory(ssrb.build());
			factories.put(schema, factory);
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
