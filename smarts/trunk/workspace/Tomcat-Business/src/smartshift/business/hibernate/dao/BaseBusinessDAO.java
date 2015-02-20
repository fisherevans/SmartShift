package smartshift.business.hibernate.dao;

import org.hibernate.Session;
import smartshift.common.hibernate.BaseDAO;

/** the base object for all business DAOs.
 * impl's MUST have a constructor that only takes a DAOContext and passes it to this super
 * @author D. Fisher Evans <contact@fisherevans.com>
 * @param <T> the model type of this dao
 */
public abstract class BaseBusinessDAO<T> extends BaseDAO<T> {
    private BusinessDAOContext context;

    /** creates the dao
     * @param context the business dao context
     * @param modelClass the model class of this dao
     */
    public BaseBusinessDAO(BusinessDAOContext context, Class<T> modelClass) {
        super(modelClass);
        this.context = context;
    }

    /**
     * @see smartshift.common.hibernate.BaseDAO#getSession()
     */
    @Override
    public Session getSession() {
        return context.getBusinessSession();
    }
    
    /** get this daos context
     * @return the business dao context
     */
    public BusinessDAOContext getContext() {
        return context;
    }
}
