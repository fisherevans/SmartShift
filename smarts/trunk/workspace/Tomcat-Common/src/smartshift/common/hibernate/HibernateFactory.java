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

    private static Configuration baseConfig;
    private static String baseURL;
	
	/**
	 * Initialized the DB factories based on the tables found in the app properties
	 */
	public synchronized static void initialize() {
        if(!AppProperties.exists(DB_URL_PROP) || AppProperties.exists(DB_SCHEMAS_PROP))
            logger.fatal("Database properties are not set!");
	    baseConfig =  new Configuration().configure();
	    baseURL = "jdbc:mysql://" + AppProperties.getProperty(DB_URL_PROP) + "/";
        String[] schemas = AppProperties.getProperty(DB_SCHEMAS_PROP).split(DB_SCHEMAS_DELIM);
        for(String schema:schemas) {
            createFactory(schema);
        }
	}
	
	/**
	 * Creates a DB factory based on a schema
	 * @param schema the schema to connect to
	 */
	public synchronized static void createFactory(String schema) {
        baseConfig.getProperties().setProperty("hibernate.connection.url", baseURL + schema);
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(baseConfig.getProperties());
        SessionFactory factory = baseConfig.buildSessionFactory(ssrb.build());
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
		for(SessionFactory factory:factories.values()) {
			if(factory != null) {
				factory.close();
			}
		}
		factories.clear();
	}
}
