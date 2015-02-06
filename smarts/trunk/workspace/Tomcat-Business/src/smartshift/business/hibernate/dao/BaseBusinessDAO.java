package smartshift.business.hibernate.dao;

import org.hibernate.Session;

/** the base object for all business DAOs.
 * impl's MUST have a constructor that only takes a DAOContext and passes it to this super
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public abstract class BaseBusinessDAO {
    private DAOContext context;

    /** creates the dao
     * @param context the business dao context
     */
    public BaseBusinessDAO(DAOContext context) {
        this.context = context;
    }

    /**
     * @return the hibernate session for this business's schema
     */
    public Session getBusinessSession() {
        return context.getBusinessSession();
    }
    
    /** get this daos context
     * @return the business dao context
     */
    public DAOContext getContext() {
        return context;
    }
}
