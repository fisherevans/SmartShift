package smartshift.common.hibernate.dao.tasks.namedquery;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.BaseDAO.NamedParameter;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that calls a named query and returns the list result
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T1> The DAO Type
 * @param <T2> The return type of executeWithSession
 */
public abstract class BaseNamedQueryTask<T1, T2> extends BaseHibernateTask<T1, T2> {
    private final String _queryName;
    
    private final NamedParameter[] _parameters;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param queryName the named query
     * @param parameters the parameters
     */
    public BaseNamedQueryTask(BaseDAO<T1> dao, String queryName, NamedParameter ... parameters) {
        super(dao);
        _queryName = queryName;
        _parameters = parameters;
    }

    public String getQueryName() {
        return _queryName;
    }

    public NamedParameter[] getParameters() {
        return _parameters;
    }

    /** Overridden method - see parent javadoc
      * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#getDebugString()
      */
    @Override
    public String getDebugString() {
        String out = _queryName + ": ";
        boolean first = true;
        for(NamedParameter np:_parameters) {
            if(!first)
                out += ", ";
            out += np.toString();
            first = false;
        }
        return out;
    }
}
