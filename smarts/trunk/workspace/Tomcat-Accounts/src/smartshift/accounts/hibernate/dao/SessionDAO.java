package smartshift.accounts.hibernate.dao;

import java.util.Date;
import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.dao.tasks.GetActiveSessionsTask;
import smartshift.accounts.hibernate.model.SessionModel;
import smartshift.accounts.util.SessionUtil;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.UniqueByCriteriaTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The access methods for Sessions
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class SessionDAO extends BaseAccountsDAO<SessionModel> {
    private static final SmartLogger logger = new SmartLogger(SessionDAO.class);

    /**
     * Initializes the object.
     */
    public SessionDAO() {
        super(SessionModel.class);
    }
    
    /**
     * Gets a task that gets an existing session by session key
     * @param key the key string
     * @return the task object
     */
    public UniqueByCriteriaTask<SessionModel> uniqueByKey(String key) {
       return uniqueByCriteria(Restrictions.eq("sessionKey", key));
    }
    
    /**
     * Gets a task that creates a new session with a random key
     * @param ubeID the user business employee relationship for this session
     * @return the task object
     */
    public AddTask<SessionModel> createNewSession(Integer ubeID) {
        SessionModel model = new SessionModel();
        model.setSessionKey(SessionUtil.generateSessionKey());
        model.setUserBusinessEmployeeID(ubeID);
        return new AddTask<SessionModel>(this, model);
    }

    /** Gets a task that gets a list of active sessions in a group
     * @param businessID the business in question
     * @param lastAccess the minimum last access time
     * @return the task object
     */
    public GetActiveSessionsTask listByBusinessAccess(Integer businessID, Date lastAccess) {
        return new GetActiveSessionsTask(this, businessID, lastAccess);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
