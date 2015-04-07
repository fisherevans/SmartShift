package smartshift.common.hibernate.dao.tasks;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that deletes a model by id
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class DeleteByIDTask<T> extends BaseHibernateTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(DeleteByIDTask.class);
    
    private Serializable _id;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param id the model id
     */
    public DeleteByIDTask(BaseDAO<T> dao, Serializable id) {
        super(dao);
        _id = id;
    }

    /**
     * deletes a model with a given id
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     * @return ALWAYS RETURNS NULL
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. ID: " + _id);
        @SuppressWarnings("unchecked")
        T model = getDAO().uniqueByID(_id).executeWithSession(session);
        logger.debug("Got Model: " + model);
        if(model != null)
            getDAO().delete(model).executeWithSession(session);
        logger.debug("Exit.");
        return null;
    }

}
