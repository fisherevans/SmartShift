package smartshift.common.hibernate.dao.tasks;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that is used as a base for other tasks
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * @param <T> The DB Model class this object should return
 */
public class DummyTask<T> extends BaseHibernateTask<T, T> {
    private static final SmartLogger logger = new SmartLogger(DummyTask.class);
    
    /**
     * Initializes the task.
     * @param dao the DAO the task belongs to
     */
    public DummyTask(BaseDAO<T> dao) {
        super(dao);
    }

    /**
     * returns null
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public T executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter.");
        logger.debug("Exit.");
        return null;
    }

}
