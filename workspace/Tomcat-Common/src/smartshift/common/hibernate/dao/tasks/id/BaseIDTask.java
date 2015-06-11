package smartshift.common.hibernate.dao.tasks.id;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task with an id
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T1> The DAO Type
 * @param <T2> The return type of executeWithSession
 */
public abstract class BaseIDTask<T1, T2> extends BaseHibernateTask<T1, T2> {
    private final Serializable _id;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param id the model id
     */
    public BaseIDTask(BaseDAO<T1> dao, Serializable id) {
        super(dao);
        _id = id;
    }

    /**
     * @return the id
     */
    public Serializable getID() {
        return _id;
    }

    /** Overridden method - see parent javadoc
      * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#getDebugString()
      */
    @Override
    public String getDebugString() {
        return _id.toString();
    }
}
