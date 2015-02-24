package smartshift.common.hibernate.dao.tasks;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that fetches a unique model based on an id
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class UniqueByIDTask<T> extends BaseHibernateTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(UniqueByIDTask.class);
    
    private final Serializable _id;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param id the model id
     */
    public UniqueByIDTask(BaseDAO<T> dao, Serializable id) {
        super(dao);
        _id = id;
    }

    /**
     * fetches a unique model by id
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. ID: " + _id);
        @SuppressWarnings("unchecked")
        T model = (T) session.get(getDAO().getModelClass(), _id);
        logger.debug("Exit. Got: " + model);
        return model;
    }

}
