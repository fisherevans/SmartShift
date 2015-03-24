package smartshift.business.updates;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import smartshift.business.security.session.UserSessionManager;
import smartshift.common.security.session.UserSession;
import smartshift.common.util.log4j.SmartLogger;

public class UpdateManager {
    private static final SmartLogger logger = new SmartLogger(UpdateManager.class);
    
    private static Map<Integer, UpdateManager> _updateManagers = new HashMap<>();
    
    private final Integer _businessID;
    
    private final Map<String, HashSet<BaseUpdate>> _updates = new HashMap<>();
    
    public UpdateManager(Integer businessID) {
        _businessID = businessID;
    }
    
    public void addUpdate(BaseUpdate update, String ignoreSession) {
        logger.debug(String.format("%d > Adding update: %s", _businessID, update));
        List<UserSession> sessions = UserSessionManager.getBusinessSessions(_businessID);
        for(UserSession session:sessions) {
            if(session.sessionID.equals(ignoreSession))
                continue;
            logger.debug(String.format("Registering update for %s = %s", session.username, update));
            getSessionUpdates(session.sessionID).add(update);
        }
    }
    
    public void deleteSessionUpdates(String sessionID) {
        if(_updateManagers.containsKey(sessionID))
            _updateManagers.remove(sessionID);
    }
    
    public HashSet<BaseUpdate> getSessionUpdates(String sessionID) {
        return getSessionUpdates(sessionID, false);
    }
    
    public HashSet<BaseUpdate> getSessionUpdates(String sessionID, boolean clear) {
        HashSet<BaseUpdate> sessionUpdates = _updates.get(sessionID);
        if(sessionUpdates == null) {
            sessionUpdates = new HashSet<BaseUpdate>();
            _updates.put(sessionID, sessionUpdates);
        }
        if(clear)
            _updates.put(sessionID, new HashSet<BaseUpdate>());
        return sessionUpdates;
    }
    
    public static UpdateManager getManager(Integer businessID) {
        UpdateManager manager = _updateManagers.get(businessID);
        if(manager == null) {
            logger.debug("Creating update manager for " + businessID);
            manager = new UpdateManager(businessID);
            _updateManagers.put(businessID, manager);
        }
        return manager;
    }
    
    public void debugPrint() {
        logger.debug("Business: " + _businessID);
        for(String session:_updates.keySet()) {
            UserSession userSession = UserSessionManager.getSession(session, true);
            if(userSession == null) {
                UserSessionManager.removeSession(session);
            } else {
                logger.debug("  Session: " + session + " - " + userSession.username);
                for(BaseUpdate update:_updates.get(session)) {
                    logger.debug("    Update: " + update);
                }
            }
        }
    }
    
    public static void debugPrintAll() {
        for(Integer businessID:_updateManagers.keySet()) {
            getManager(businessID).debugPrint();
        }
    }
}
