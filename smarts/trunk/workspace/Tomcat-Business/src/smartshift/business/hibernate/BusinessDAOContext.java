package smartshift.business.hibernate;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import smartshift.business.hibernate.dao.BaseBusinessDAO;
import smartshift.common.hibernate.DAOContext;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/** context for DAOs per business
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class BusinessDAOContext implements DAOContext {
    private static final SmartLogger logger = new SmartLogger(BusinessDAOContext.class);
    
    private static Map<Integer, BusinessDAOContext> contexts = new HashMap<>();
    
    private final Integer businessID;
    
    @SuppressWarnings("rawtypes")
    private final Map<Class, BaseBusinessDAO> daos = new HashMap<>();
    
    /** creates a context with a businessID
     * @param businessID
     */
    public BusinessDAOContext(Integer businessID) {
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
    @Override
    public Session getSession() {
        return HibernateFactory.getSession(AppConstants.DB_SCHEMA_BUSINESS_BASE + getBusinessID());
    }

    /** gets a dao context
     * @param businessID the business id of the contxt
     * @return the context. makes one if it doesn't exist
     */
    public static BusinessDAOContext business(Integer businessID) {
        BusinessDAOContext context = contexts.get(businessID);
        if(context == null) {
            context = new BusinessDAOContext(businessID);
            contexts.put(businessID, context);
        }
        return context;
    }
    
    /** gets the dao of the given class
     * @param <T> the dao class
     * @param clazz the class
     * @return the dao. creates it if it doesn't exist
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends BaseBusinessDAO> T dao(Class<T> clazz) {
        try {
            T dao = (T) daos.get(clazz);
            if(dao == null) {
                dao = clazz.getConstructor(BusinessDAOContext.class).newInstance(this);
                daos.put(clazz, dao);
            }
            return dao;
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
    @SuppressWarnings("rawtypes")
    public static <T extends BaseBusinessDAO> T businesDAO(Integer businessID, Class<T> clazz) {
        return business(businessID).dao(clazz);
    }
    
    /**
     * see DAOContext.getContextID()
     */
    @Override
    public Object getContextID() {
        return businessID;
    }
}
