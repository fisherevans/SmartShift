package smartshift.common.hibernate.dao.tasks.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 *A base task with a model
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T1> The DAO Type
 * @param <T2> The return type of executeWithSession
 */
public abstract class BaseModelTask<T1, T2> extends BaseHibernateTask<T1, T2> {
    private T1 _model;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param model the newly made model to add
     */
    public BaseModelTask(BaseDAO<T1> dao, T1 model) {
        super(dao);
        _model = model;
    }
    
    /**
     * @return the model to be added
     */
    public T1 getModel() {
        return _model;
    }
    
    /**
     * @param model the model to be set
     */
    public void setModel(T1 model) {
        _model = model;
    }

    /** Overridden method - see parent javadoc
      * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#getDebugString()
      */
    @Override
    public String getDebugString() {
        return _model.toString();
    }
}
