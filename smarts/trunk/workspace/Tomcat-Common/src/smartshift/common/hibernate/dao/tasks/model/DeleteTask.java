package smartshift.common.hibernate.dao.tasks.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that deletes a model
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class DeleteTask<T> extends BaseModelTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(DeleteTask.class);
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param model the model to delete
     */
    public DeleteTask(BaseDAO<T> dao, T model) {
        super(dao, model);
    }

    /**
     * deletes a model
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     * @return ALWAYS RETURNS NULL
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. Model: " + getModel());
        session.delete(getModel());
        logger.debug("Exit.");
        return null;
    }
}
