package smartshift.common.hibernate.dao.tasks;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that adds a new model to the DB
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class AddTask<T> extends BaseHibernateTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(AddTask.class);
    
    private T _model;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param model the newly made model to add
     */
    public AddTask(BaseDAO<T> dao, T model) {
        super(dao);
        _model = model;
    }

    /**
     * saves a model
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. Model: " + _model);
        session.save(_model);
        logger.debug("Exit. Got: " + _model);
        return _model;
    }

}
