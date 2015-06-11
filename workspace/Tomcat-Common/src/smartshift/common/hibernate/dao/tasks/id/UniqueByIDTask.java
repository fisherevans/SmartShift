package smartshift.common.hibernate.dao.tasks.id;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that fetches a unique model based on an id
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class UniqueByIDTask<T> extends BaseIDTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(UniqueByIDTask.class);
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param id the model id
     */
    public UniqueByIDTask(BaseDAO<T> dao, Serializable id) {
        super(dao, id);
    }

    /**
     * fetches a unique model by id
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. ID: " + getID());
        @SuppressWarnings("unchecked")
        T model = (T) session.get(getDAO().getModelClass(), getID());
        logger.debug("Exit. Got: " + model);
        return model;
    }
}
