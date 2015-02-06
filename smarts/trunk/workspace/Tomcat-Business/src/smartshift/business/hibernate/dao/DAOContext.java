package smartshift.business.hibernate.dao;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/** context for DAOs per business
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class DAOContext {
    private static final SmartLogger logger = new SmartLogger(DAOContext.class);
    
    private static Map<Integer, DAOContext> contexts = new HashMap<>();
    
    private Integer businessID;
    
    @SuppressWarnings("rawtypes")
    private Map<Class, Object> daos = new HashMap<>();
    
    /** creates a context with a businessID
     * @param businessID
     */
    public DAOContext(Integer businessID) {
        this.businessID = businessID;
    }
    
    /** the business id of the context
     * @return the id
     */
    public Integer getBusinessID() {
        return businessID;
    }

    /**
     * @return the hibernate session for this business's schema
     */
    public Session getBusinessSession() {
        return HibernateFactory.getSession(AppConstants.DB_SCHEMA_BUSINESS_BASE + getBusinessID());
    }

    /** gets a dao context
     * @param businessID the business id of the contxt
     * @return the context. makes one if it doesn't exist
     */
    public static DAOContext business(Integer businessID) {
        DAOContext context = contexts.get(businessID);
        if(context == null) {
            context = new DAOContext(businessID);
            contexts.put(businessID, context);
        }
        return context;
    }
    
    /** gets the dao of the given class
     * @param <T> the dao class
     * @param clazz the class
     * @return the dao. creates it if it doesn't exist
     */
    @SuppressWarnings("unchecked")
    public <T> T dao(Class<T> clazz) {
        try {
            Object dao = daos.get(clazz);
            if(dao == null) {
                dao = clazz.getConstructor(DAOContext.class).newInstance(this);
                daos.put(clazz, dao);
            }
            return (T) dao;
        } catch(Exception e) {
            logger.error("Failed to create DAO: " + clazz + " " + businessID, e);
            throw new IllegalArgumentException(clazz.getCanonicalName() + " is an invalid DAO object!");
        }
    }
    
    /** get the DAO for a business
     * @param <T> the dao type
     * @param businessID the business d
     * @param clazz the dao class
     * @return the dao instance for this business
     */
    public static <T> T businesDAO(Integer businessID, Class<T> clazz) {
        return business(businessID).dao(clazz);
    }
}
