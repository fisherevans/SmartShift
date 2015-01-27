package smartshift.common.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import smartshift.common.util.PrimativeUtils;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * Manages hibernate connections for each business schema
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class BusinessDatabaseManager {
    private static final SmartLogger logger = new SmartLogger(BusinessDatabaseManager.class);
    private static Map<Integer, String> businesses = new HashMap<>();
    
    /**
     * Registers a business name and connects to the business schema
     * @param businessID the business id
     * @param businessName the business name
     * @return true if schema connected
     */
    public synchronized static boolean connectBusinessSchema(Integer businessID, String businessName) {
        try {
            logger.info("Connecting to " + businessID + ":" + businessName + " db schema.");
            String schema = getBusinessSchema(businessID);
            SessionFactory sf = HibernateFactory.getFactory(schema);
            if(sf != null) {
                logger.info("Attempted to connect to " + businessID + ":" + businessName + ", but it was already connected.");
                return false; 
            }
            HibernateFactory.createFactory(schema);
        } catch(Exception e) {
            logger.error("Failed to connect to business schema", e);
            return false;
        }
        businesses.put(businessID, businessName);
        return true;
    }
    
    /**
     * fetches a business session factory.
     * @param businessID the business id
     * @return the session factory. null if not connected or present.
     */
    public synchronized static SessionFactory getBusinessSessionFactory(Integer businessID) {
        if(businesses.containsKey(businessID))
            return HibernateFactory.getFactory(getBusinessSchema(businessID));
        return null;
    }

    /**
     * fetches a business session.
     * @param businessID the business id
     * @return the session. null if not connected or present.
     */
    public synchronized static Session getBusinessSession(Integer businessID) {
        if(businesses.containsKey(businessID))
            return HibernateFactory.getSession(getBusinessSchema(businessID));
        return null;
    }
    
    /**
     * @param businessID the business id
     * @return the name of the business
     */
    public synchronized static String getBusinessName(Integer businessID) {
        return businesses.get(businessID);
    }
    
    /** Disconnect all schemas exluding any business id's passed
     * @param exludeIDs the ids not to disconnect
     */
    public synchronized static void disconnectAllSchemas(Integer ... exludeIDs) {
        List<Integer> disconnectIDs = new ArrayList<Integer>(businesses.keySet().size() - exludeIDs.length);
        for(Integer businessID:businesses.keySet()) {
            if(!PrimativeUtils.inArray(businessID, exludeIDs))
                disconnectIDs.add(businessID);
        }
        for(Integer disconnectID:disconnectIDs)
            disconnectSchema(disconnectID);
    }
    
    /** disconnect a single business database connection
     * @param businessID the business id to disconnect from
     */
    public synchronized static void disconnectSchema(Integer businessID) {
        businesses.remove(businessID);
        HibernateFactory.closeFactory(getBusinessSchema(businessID));
    }
    
    /**
     * @param businessID the business id
     * @return the db schema name for that business
     */
    public synchronized static String getBusinessSchema(Integer businessID) {
        return AppConstants.DB_SCHEMA_BUSINESS_BASE + businessID;
    }
}
