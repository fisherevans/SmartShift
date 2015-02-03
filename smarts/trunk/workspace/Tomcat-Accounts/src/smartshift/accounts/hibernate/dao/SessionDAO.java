package smartshift.accounts.hibernate.dao;

import java.security.SecureRandom;
import java.util.Date;
import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.model.SessionModel;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The access methods for Sessions
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class SessionDAO extends BaseAccountsDAO {
    private static final SmartLogger logger = new SmartLogger(SessionDAO.class);
    
    /**
     * The characters to use in session keys
     */
    private static final String KEY_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
    
    /**
     * Length of session keys
     */
    private static final Integer KEY_LENGTH = 255;
    
    /**
     * Gets an existing session
     * @param ubeID the user business employee relation for this key
     * @param key the key string
     * @return the session object, null if it doesn't exist
     */
    public static SessionModel getSession(Integer ubeID, String key) {
        logger.debug("getSession() Enter");
        SessionModel session = GenericHibernateUtil.uniqueByCriterea(getAccountsSession(), SessionModel.class,
                Restrictions.eq("userBusinessEmployeeID", ubeID), Restrictions.eq("sessionKey", key));
        return session;
    }
    
    /**
     * Updates a session's last timestamp with the current time
     * @param session the session the update
     * @return true if the session was updated
     */
    public static boolean updateSessionTimestamp(SessionModel session) {
        logger.debug("updateSessionTimestamp() Enter, use now");
        return updateSessionTimestamp(session, new Date());
    }
    
    /**
     * Updates a session's timestamp
     * @param session the session to update
     * @param ts the new time stamp
     * @return true if the session updated
     */
    public static boolean updateSessionTimestamp(SessionModel session, Date ts) {
        logger.debug("updateSessionTimestamp() Enter");
        session.setLastActivityTimestamp(ts);
        try {
            GenericHibernateUtil.save(getAccountsSession(), session);
        } catch(DBException e) {
            logger.debug("updateSessionTimestamp() failed to update timestamp", e);
            return false;
        }
        return true;
    }
    
    /**
     * creates a new session with a random key
     * @param ube the user business employee relationship for this session
     * @return the new session
     */
    public static SessionModel createSession(Integer ubeID) {
        logger.debug("createSession() Enter");
        try {
            SessionModel session = new SessionModel();
            session.setSessionKey(generateSessionKey(ubeID));
            session.setUserBusinessEmployeeID(ubeID);
            GenericHibernateUtil.save(getAccountsSession(), session);
            return session;
        } catch(Exception e) {
            logger.debug("createSession() Failed", e);
        }
        return null;
    }
    
    /**
     * Destroys a session
     * @param session the session to destroy
     * @return true if the session was destroyed
     */
    public static boolean destroySession(SessionModel session) {
        logger.debug("destroySession() Enter");
        try {
            GenericHibernateUtil.delete(getAccountsSession(), session);
        } catch(DBException e) {
            logger.debug("destroySession() failed", e);
            return false;
        }
        return true;
    }
    
    /**
     * Generate a new session key unique to the user business employee relationship
     * @param ube the relationship to use
     */
    private static String generateSessionKey(Integer ubeID) {
        logger.debug("generateSessionKey() Enter");
        String key = "";
        do {
            logger.debug("generateSessionKey() Generating key");
            key = getRandomSessionKey(KEY_LENGTH);
        } while(getSession(ubeID, key) != null);
        return key;
    }
    
    /**
     * get random string using the key alphabet
     * @param length the length of the random string
     * @return the string
     */
    private static String getRandomSessionKey(int length) {
        logger.debug("getRandomSessionKey() Enter");
        SecureRandom random = new SecureRandom();
        StringBuffer buffer = new StringBuffer(KEY_ALPHABET.length());
        for(int i = 0;i < KEY_ALPHABET.length();i++) {
            buffer.append(KEY_ALPHABET.charAt(random.nextInt(KEY_ALPHABET.length())));
        }
        return buffer.toString();
    }
}