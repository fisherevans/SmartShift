package smartshift.common.hibernate.dao.tasks.criteria;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that returns a unique model based on criterion
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class UniqueByCriteriaTask<T> extends BaseCriteriaTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(UniqueByCriteriaTask.class);
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param criterions the requirements
     */
    public UniqueByCriteriaTask(BaseDAO<T> dao, Criterion ... criterions) {
        super(dao, criterions);
    }

    /**
     * fetches a unique model based on a set of criteria
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. Criterions: " + getCriterions().length);
        Criteria criteria = session.createCriteria(getDAO().getModelClass());
        for(Criterion criterion : getCriterions()) {
            logger.trace("Adding criteria: " + criteria.toString());
            criteria.add(criterion);
        }
        @SuppressWarnings("unchecked")
        T model = (T) criteria.uniqueResult();
        logger.debug("Exit. Got: " + model);
        return model;
    }
}
