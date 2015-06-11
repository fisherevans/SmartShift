package smartshift.common.hibernate.dao.tasks;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.common.hibernate.DBAction;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.HibernateTaskQueue;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The base task object.
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T1> The DAO Type
 * @param <T2> The return type of executeWithSession
 */
public abstract class BaseHibernateTask<T1, T2> {
    private static final SmartLogger logger = new SmartLogger(BaseHibernateTask.class);
    
    private final BaseDAO<T1> _dao;
    
    /**
     * Initializes the object.
     * @param dao
     */
    public BaseHibernateTask(BaseDAO<T1> dao) {
        _dao = dao;
    }
    
    /**
     * @param session the session to execute this task with
     * @return the return object
     * @throws HibernateException if a hibernate error occurs
     */
    public abstract T2 executeWithSession(Session session) throws HibernateException;
    
    /**
     * Makes a session and executes this task.
     * Will auto commit or roll back depending on exceptions thrown.
     * See the java doc of executeWithSession(Session) for the task def
     * @return the generic return object
     * @throws HibernateException if a hibernate error occurs
     */
    public T2 execute() throws HibernateException {
        logger.trace("Enter");
        DBAction action = new DBAction(_dao.getSession());
        try {
            T2 result = executeWithSession(action.session());
            action.commit();
            logger.trace("Exit");
            return result;
        } catch(HibernateException e) {
            logger.warn("An error was thrown, rolling back!", e);
            action.rolback();
            throw e;
        }
    }
    
    /**
     * enqueues this task in the hibernate task queue corresponding to this task's DAO context
     */
    public void enqueue() {
        HibernateTaskQueue.getQueue(_dao.getDAOContext()).enqueueTask(this);
    }
    
    /** compares this task to another task and detirmines if they cancle themselves out
     * @param otherTask the other task to compare.
     * @return true if this task and the other cancel each other out and both should be ignored
     */
    @SuppressWarnings("rawtypes")
    public boolean equalizes(BaseHibernateTask otherTask) {
        return false;
    }

    /**
     * tests if this task overrides another task (meaning the other task should be ignored)
     * @param otherTask the other task to compare to
     * @return returns true if the other task should be ignored.
     */
    @SuppressWarnings("rawtypes")
    public boolean overrides(BaseHibernateTask otherTask) {
        return false;
    }

    /**
     * tests whether this task and another can be compounded into one task
     * @param otherTask the other task to check
     * @return null if they cannot be compounded, the new compound task if they can that replaces both
     */
    @SuppressWarnings("rawtypes")
    public BaseHibernateTask compounds(BaseHibernateTask otherTask) {
        return null;
    }
    
    protected BaseDAO<T1> getDAO() {
        return _dao;
    }
    
    /** Overridden method - see parent javadoc
      * @see java.lang.Object#toString()
      */
    @Override
    public String toString() {
        return _dao.getModelClass().getSimpleName() + "." + this.getClass().getSimpleName() + " -> " + getDebugString();
    }
    
    /**
     * @return a string used for debugging print outs
     */
    public abstract String getDebugString();
}
