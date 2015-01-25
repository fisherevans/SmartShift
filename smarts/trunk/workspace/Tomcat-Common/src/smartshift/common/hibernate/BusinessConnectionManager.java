package smartshift.common.hibernate;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import smartshift.common.util.properties.AppConstants;

/**
 * Manages hibernate connections for each business schema
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class BusinessConnectionManager {
    private static final Logger logger = Logger.getLogger(BusinessConnectionManager.class);
    private static Map<Integer, String> businesses = new HashMap<>();
    
    /**
     * Registers a business name and connects to the business schema
     * @param businessID the business id
     * @param businessName the business name
     */
    public static void connectBusinessSchema(Integer businessID, String businessName) {
        businesses.put(businessID, businessName);
        logger.info("Connecting to " + businessID + ":" + businessName + " db schema.");
        String schema = getBusinessSchema(businessID);
        SessionFactory sf = HibernateFactory.getFactory(schema);
        if(sf != null) {
            logger.info("Attempted to connect to " + businessID + ":" + businessName + ", but it was already connected.");
            return; 
        }
        HibernateFactory.createFactory(schema);
    }
    
    /**
     * fetches a business session factory.
     * @param businessID the business id
     * @return the session factory. null if not connected or present.
     */
    public static SessionFactory getBusinessSessionFactory(Integer businessID) {
        if(businesses.containsKey(businessID))
            return HibernateFactory.getFactory(getBusinessSchema(businessID));
        return null;
    }

    /**
     * fetches a business session.
     * @param businessID the business id
     * @return the session. null if not connected or present.
     */
    public static Session getBusinessSession(Integer businessID) {
        if(businesses.containsKey(businessID))
            return HibernateFactory.getSession(getBusinessSchema(businessID));
        return null;
    }
    
    /**
     * @param businessID the business id
     * @return the name of the business
     */
    public static String getBusinessName(Integer businessID) {
        return businesses.get(businessID);
    }
    
    /**
     * @param businessID the business id
     * @return the db schema name for that business
     */
    public static String getBusinessSchema(Integer businessID) {
        return AppConstants.DB_SCHEMA_BUSINESS_BASE + businessID;
    }
}
