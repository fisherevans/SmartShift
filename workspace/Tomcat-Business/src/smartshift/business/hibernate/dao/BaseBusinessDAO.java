package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.common.hibernate.DynamicNextID;
import smartshift.common.hibernate.dao.BaseDAO;

/** the base object for all business DAOs.
 * impl's MUST have a constructor that only takes a DAOContext and passes it to this super
 * @author D. Fisher Evans <contact@fisherevans.com>
 * @param <T> the model type of this dao
 */
public abstract class BaseBusinessDAO<T> extends BaseDAO<T> {
    /** creates the dao
     * @param context the business dao context
     * @param modelClass the model class of this dao
     */
    public BaseBusinessDAO(BusinessDAOContext context, Class<T> modelClass) {
        super(context, modelClass);
    }
}
