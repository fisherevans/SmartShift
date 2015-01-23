package smartshift.common.security.session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dom4j.IllegalAddException;
import smartshift.common.util.collections.ROSet;

/**
 * A class the manage the current user sessions
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class UserSessionManager {
    private static final Logger logger = Logger.getLogger(UserSessionManager.class);
    
    private static Map<String, UserSession> sessions = new HashMap<>();
    
    /**
     * Adds a session to the system
     * @param session the session to add
     */
    public static synchronized void addSession(UserSession session) {
        logger.info("Adding session: " + session.sessionID);
        if(sessions.get(session.sessionID) != null) {
            throw new IllegalAddException("A session already exists with the sessionID: " + session.sessionID);
        }
        sessions.put(session.sessionID, session);
    }

    /**
     * gets a session from the system
     * @param sessionID the session id to fetch
     * @return the user session obect - null if none exist with the id given
     */
    public static synchronized UserSession getSession(String sessionID) {
        return sessions.get(sessionID);
    }

    /**
     * Removes a session from the system
     * @param sessionID the session id to remove
     * @return the user session obect remove d- null if none were
     */
    public static synchronized UserSession removeSession(String sessionID) {
        logger.info("Removing session: " + sessionID);
        return sessions.remove(sessionID);
    }

    /**
     * Fetches the set of all sessionIds
     * @return the ro set of sessions ids
     */
    public static synchronized ROSet<String> getAllSessionIds() {
        return new ROSet<String>(sessions.keySet());
    }
    
    /**
     * Validates that a session exists and is still active. IF a session is found and is not active, it will be removed.
     * @param username The username of the sessions
     * @param sessionId the session id to lookup
     * @param updateSession set to true to update the sessions last activity timestamp with now if one is found
     * @return true if the session is valid, false if not
     */
    public static synchronized boolean validationSessionInfo(String username, String sessionId, boolean updateSession) {
        UserSession session = getSession(sessionId);
        if(session == null || !session.username.equals(username))
            return false;
        if(!session.stillActive()) {
            removeSession(sessionId);
            return false;
        }
        if(updateSession)
            session.updateLastActivity();
        return true;
    }
    
    /**
     * Removes any inactive sessions from the system (last activity was too long ago
     * @return the number of sessions removed
     */
    public static synchronized int clean() {
        int sessionsRemoved = 0;
        String sessionList = "";
        for(Iterator<Map.Entry<String, UserSession>> it = sessions.entrySet().iterator(); it.hasNext(); ) {
          Map.Entry<String, UserSession> entry = it.next();
          if(entry.getValue() == null || !entry.getValue().stillActive()) {
              sessionList += entry.getValue() != null ? entry.getValue().sessionID + ", " : "null, ";
              sessionsRemoved++;
              it.remove();
          }
        }
        logger.info("Removed " + sessionsRemoved + " sessions with clean(): " + sessionList);
        return sessionsRemoved;
    }
    
    /**
     * Removes ALL sessions from the system 
     * @return the number of sessions removed
     */
    public static synchronized int invalidateAllSessions() {
        int sessionsRemoved = 0;
        String sessionList = "";
        for(Iterator<Map.Entry<String, UserSession>> it = sessions.entrySet().iterator(); it.hasNext(); ) {
          Map.Entry<String, UserSession> entry = it.next();
          sessionList += entry.getValue() != null ? entry.getValue().sessionID + ", " : "null, ";
          sessionsRemoved++;
          it.remove();
        }
        logger.info("Removed " + sessionsRemoved + " sessions with invalidateAllSessions(): " + sessionList);
        return sessionsRemoved;
    }
}
