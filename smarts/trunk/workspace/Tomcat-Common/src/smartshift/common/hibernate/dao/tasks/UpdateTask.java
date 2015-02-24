package smartshift.common.hibernate.dao.tasks;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that updates an existing model
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class UpdateTask<T> extends BaseHibernateTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(UpdateTask.class);
    
    private T _model;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param model the existing made model to update
     */
    public UpdateTask(BaseDAO<T> dao, T model) {
        super(dao);
        _model = model;
    }

    /**
     * updates an existing model
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. Model: " + _model);
        session.update(_model);
        logger.debug("Exit. Got: " + _model);
        return _model;
    }

}
