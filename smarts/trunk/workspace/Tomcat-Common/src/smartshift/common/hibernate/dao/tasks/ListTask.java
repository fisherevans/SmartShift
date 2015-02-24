package smartshift.common.hibernate.dao.tasks;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that lists a set of models
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return as list
 */
public class ListTask<T> extends BaseHibernateTask<T, ROCollection<T>> {
    private static final SmartLogger logger = new SmartLogger(ListTask.class);
    
    private final Criterion[] _criterions;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param criterions the requirements
     */
    public ListTask(BaseDAO<T> dao, Criterion ... criterions) {
        super(dao);
        _criterions = criterions;
    }

    /**
     * fetches a list of models based on a set of criteria
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public ROCollection<T> executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. Criterions: " + _criterions.length);
        Criteria criteria = session.createCriteria(getDAO().getModelClass());
        for(Criterion criterion : _criterions) {
            logger.trace("Adding criteria: " + criteria.toString());
            criteria.add(criterion);
        }
        @SuppressWarnings("unchecked")
        List<T> models = criteria.list();
        logger.debug("Exit. Got: " + (models == null ? null : models.size()));
        return ROCollection.wrap(models);
    }

}
