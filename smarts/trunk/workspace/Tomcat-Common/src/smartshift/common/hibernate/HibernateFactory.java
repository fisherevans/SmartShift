package smartshift.common.hibernate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.SessionFactoryImpl;
import smartshift.common.util.PrimativeUtils;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A hibernate factory class, used to access the database using objects
 *          and annotation
 */
public class HibernateFactory {
    private static SmartLogger logger = new SmartLogger(HibernateFactory.class);
    
    @SuppressWarnings("rawtypes")
    private static Set<Class> annotatedClasses = new HashSet<Class>();
	
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
	    for(@SuppressWarnings("rawtypes") Class clazz:annotatedClasses)
	        cfg.addAnnotatedClass(clazz);
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
        logger.info("Closing all hibernate sessison factories.");
        Set<String> schemas = new HashSet<>(factories.keySet());
		for(String schema:schemas) {
			closeFactory(schema);
		}
		factories.clear();
	}

    /** close and remove a single business schema
     * @param schema the schema to disconnect from
     */
    public static void closeFactory(String schema) {
        SessionFactory sf =  getFactory(schema);
        if(sf != null) {
            if(sf instanceof SessionFactoryImpl) {
                SessionFactoryImpl sfi = (SessionFactoryImpl) sf;
                ConnectionProvider cp = sfi.getConnectionProvider();
                if(cp instanceof C3P0ConnectionProvider) {
                    C3P0ConnectionProvider ccp = (C3P0ConnectionProvider) cp;
                    logger.info("Closing C3P0 connection provider: " + ccp.toString());
                    ((C3P0ConnectionProvider) cp).stop();
                }
            }
            sf.close();
        }
        factories.remove(schema);
    }
    
    /** Adds a class to the list of classes to load in the session factories 
     * @param clazz the annotated hibernat class
     */
    public static void addAnnotatedClass(@SuppressWarnings("rawtypes") Class clazz) {
        logger.info("Adding Hibernate Class: " + clazz.getSimpleName());
        annotatedClasses.add(clazz);
    }
}
