package smartshift.accounts.hibernate.dao.tasks;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import smartshift.accounts.hibernate.model.SessionModel;
import smartshift.accounts.hibernate.model.custom.GetActiveSessionsModel;
import smartshift.common.hibernate.dao.BaseDAO;
import smartshift.common.hibernate.dao.tasks.BaseHibernateTask;
import smartshift.common.hibernate.dao.tasks.DummyTask;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * A task that gets a list of active sessions in a group
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 */
public class GetActiveSessionsTask extends BaseHibernateTask<SessionModel, ROCollection<GetActiveSessionsModel>> {
    private static final SmartLogger logger = new SmartLogger(DummyTask.class);
    
    private Integer _businessID;
    
    private Date _lastAccess;
    
    /**
     * Initializes the task.
     * @param businessID the business in question
     * @param lastAccess the minimum last access time
     * @param dao the DAO the task belongs to
     */
    public GetActiveSessionsTask(BaseDAO<SessionModel> dao, Integer businessID, Date lastAccess) {
        super(dao);
        _businessID = businessID;
        _lastAccess = lastAccess;
    }

    /**
     * gets a list of active sessions in a group
     * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#executeWithSession(org.hibernate.Session)
     */
    @Override
    public ROCollection<GetActiveSessionsModel> executeWithSession(Session session) throws HibernateException {
        logger.debug("Enter.");
        @SuppressWarnings("unchecked")
        List<GetActiveSessionsModel> models  = getDAO().prepareNamedQuery(session, SessionModel.GET_ACTIVE_SESSIONS,
                new BaseDAO.NamedParameter(SessionModel.GET_ACTIVE_SESSIONS_BUSINESS_ID, _businessID),
                new BaseDAO.NamedParameter(SessionModel.GET_ACTIVE_SESSIONS_LAST_ACCESS, _lastAccess)).list();
        logger.debug("Exit.");
        return ROCollection.wrap(models);
    }

    /** Overridden method - see parent javadoc
      * @see smartshift.common.hibernate.dao.tasks.BaseHibernateTask#getDebugString()
      */
    @Override
    public String getDebugString() {
        return "B: " + _businessID + " - D: " + _lastAccess.toString();
    }
}
