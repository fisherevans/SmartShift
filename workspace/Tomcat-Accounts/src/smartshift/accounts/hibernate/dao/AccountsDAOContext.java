package smartshift.accounts.hibernate.dao;

import java.util.HashMap;
import java.util.Map;
import smartshift.common.util.log4j.SmartLogger;

/** context for DAOs per business.
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public abstract class AccountsDAOContext {
    private static final SmartLogger logger = new SmartLogger(AccountsDAOContext.class);
    
    @SuppressWarnings("rawtypes")
    private static Map<Class, BaseAccountsDAO> daos = new HashMap<>();
    
    /** gets the dao of the given class
     * @param <T> the dao class
     * @param clazz the class
     * @return the dao. creates it if it doesn't exist
     */
    @SuppressWarnings("rawtypes")
    public static <T extends BaseAccountsDAO> T dao(Class<T> clazz) {
        try {
            @SuppressWarnings("unchecked")
            T dao = (T) daos.get(clazz);
            if(dao == null) {
                dao = clazz.getConstructor().newInstance();
                daos.put(clazz, dao);
            }
            return dao;
        } catch(Exception e) {
            logger.error("Failed to create DAO: " + clazz, e);
            throw new IllegalArgumentException(clazz.getCanonicalName() + " is an invalid DAO object!");
        }
    }
}
