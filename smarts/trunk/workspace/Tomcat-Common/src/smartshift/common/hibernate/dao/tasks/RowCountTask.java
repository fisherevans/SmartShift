package smartshift.common.hibernate.dao.tasks;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 * @param <T> the dao type
 * 
 * task to get row count based on criterion
 */
public class RowCountTask<T> extends BaseHibernateTask<T, Integer>{
    private static final SmartLogger logger = new SmartLogger(RowCountTask.class);
    
    private final Criterion[] _criterions;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param criterions the requirements
     */
    public RowCountTask(BaseDAO<T> dao, Criterion ... criterions) {
        super(dao);
        _criterions = criterions;
    }

    /**
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public Integer executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter. Criterions: " + _criterions.length);
        Criteria criteria = session.createCriteria(getDAO().getModelClass());
        for(Criterion criterion : _criterions) {
            logger.trace("Adding criteria: " + criteria.toString());
            criteria.add(criterion);
        }
        Integer rowCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
        logger.debug("Exit. Got: " + rowCount);
        return rowCount;
    }
}
