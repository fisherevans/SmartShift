package smartshift.accounts.hibernate.dao;

import org.hibernate.Session;
import smartshift.common.hibernate.BaseDAO;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.properties.AppConstants;

/**
 * Base data acess object for accounts. Implementors MUST have a default constructor
 * @author D. Fisher Evans <contact@fisherevans.com>
 * @param <T> the db model class
 *
 */
public abstract class BaseAccountsDAO<T> extends BaseDAO<T> {
    
    /**
     * Initializes the object.
     * @param modelClass
     */
    public BaseAccountsDAO(Class<T> modelClass) {
        super(modelClass);
    }

    /**
     * @see smartshift.common.hibernate.BaseDAO#getSession()
     */
    @Override
    public Session getSession() {
        return HibernateFactory.getSession(AppConstants.DB_SCHEMA_ACCOUNTS);
    }
}
