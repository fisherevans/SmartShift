package smartshift.accounts.hibernate.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.model.SessionModel;
import smartshift.accounts.hibernate.model.custom.GetActiveSessionsModel;
import smartshift.accounts.util.SessionUtil;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
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
     * Gets an existing session
     * @param key the key string
     * @return the session object, null if it doesn't exist
     */
    public SessionModel uniqueByKey(String key) {
        SessionModel session = uniqueByCriteria(Restrictions.eq("sessionKey", key));
        return session;
    }
    
    /**
     * creates a new session with a random key
     * @param ubeID the user business employee relationship for this session
     * @return the new session
     * @throws DBException 
     */
    public SessionModel createNewSession(Integer ubeID) throws DBException {
        SessionModel model = new SessionModel();
        model.setSessionKey(SessionUtil.generateSessionKey());
        model.setUserBusinessEmployeeID(ubeID);
        model = add(model);
        return model;
    }

    /** gets a list of active sessions in a group
     * @param businessID the business in question
     * @param lastAccess the minimum last access time
     * @return the list of employees ids
     */
    public ROCollection<GetActiveSessionsModel> listByBusiessAccess(Integer businessID, Date lastAccess) {
        @SuppressWarnings("unchecked")
        List<GetActiveSessionsModel> models = getSession()
                .getNamedQuery(SessionModel.GET_ACTIVE_SESSIONS)
                .setParameter(SessionModel.GET_ACTIVE_SESSIONS_BUSINESS_ID, businessID)
                .setParameter(SessionModel.GET_ACTIVE_SESSIONS_LAST_ACCESS, lastAccess)
                .list();
        return ROCollection.wrap(models);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
