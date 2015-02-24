package smartshift.common.hibernate.dao.tasks;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that deletes all models matching a set of criteria
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class DeleteByCriteriaTask<T> extends BaseHibernateTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(DeleteByCriteriaTask.class);
    
    private Criterion[] _criterions;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param criterions the requirements
     */
    public DeleteByCriteriaTask(BaseDAO<T> dao, Criterion ... criterions) {
        super(dao);
        _criterions = criterions;
    }

    /**
     * deletes all models matching the given criteria
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     * @return ALWAYS RETURNS NULL
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. Criterions: " + _criterions.length);
        ROCollection<T> models = getDAO().list(_criterions).executeWithSession(session);
        logger.debug("Deleting: " + models.size());
        for(T model:models)
            session.delete(model);
        logger.debug("Exit.");
        return null;
    }

}
