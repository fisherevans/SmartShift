package smartshift.common.hibernate.dao.tasks.criteria;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * base task with a set of criteria
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T1> The DAO Type
 * @param <T2> The return type of executeWithSession
 */
public abstract class BaseCriteriaTask<T1, T2> extends BaseHibernateTask<T1, T2> {
    private Criterion[] _criterions;
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     * @param criterions the requirements
     */
    public BaseCriteriaTask(BaseDAO<T1> dao, Criterion ... criterions) {
        super(dao);
        _criterions = criterions;
    }

    /**
     * @return the criterions
     */
    public Criterion[] getCriterions() {
        return _criterions;
    }

    /** Overridden method - see parent javadoc
      * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#getDebugString()
      */
    @Override
    public String getDebugString() {
        String out = "";
        boolean first = true;
        for(Criterion crit:_criterions) {
            if(!first)
                out += ", ";
            out += crit.toString();
            first = false;
        }
        return out;
    }
}
