package smartshift.common.hibernate.dao.tasks;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.BaseDAO.NamedParameter;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that calls a named query and returns the unique result
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return as list
 */
public class UniqueNamedQueryTask<T> extends BaseHibernateTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(UniqueNamedQueryTask.class);

    private final String _queryName;
    
    private final NamedParameter[] _parameters;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param queryName the named query
     * @param parameters the parameters
     */
    public UniqueNamedQueryTask(BaseDAO<T> dao, String queryName, NamedParameter ... parameters) {
        super(dao);
        _queryName = queryName;
        _parameters = parameters;
    }

    /**
     * calls a named query and returns the unique result
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. Name: " + _queryName);
        Query query = getDAO().prepareNamedQuery(session, _queryName, _parameters);
        @SuppressWarnings("unchecked")
        T model = (T) query.uniqueResult();
        logger.debug("Exit. Got: " + model);
        return model;
    }

}
