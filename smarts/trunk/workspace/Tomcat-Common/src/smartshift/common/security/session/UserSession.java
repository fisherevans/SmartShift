package smartshift.common.security.session;

/**
 * Holds a session for a user. This is to be created by the accounts application and sent to business applications
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserSession {
    /** The username for the session */
    public final String username;
    
    /** The identifying session id */
    public final String sessionID;
    
    /** The identifying buisness id */
    public final Integer businesID;
    
    /** The identifying employee id */
    public final Integer employeeID;
    
    /** The timeout in milliseconds */
    public final long timeOutPeriod;
    
    /** the last time this session was used */
    private long _lastActivity;
    
    /**
     * Creates the session with a last activty of now
     * @param username the username
     * @param sessionID the session identifier
     * @param businesID the business id
     * @param employeeID the employee id
     * @param timeOutPeriod the timeout period in ms
     */
    public UserSession(String username, String sessionID, Integer businesID, Integer employeeID, long timeOutPeriod) {
        this(username, sessionID, businesID, employeeID, timeOutPeriod, System.currentTimeMillis());
    }
    
    /**
     * Initializes a session with the given initial time
     * @param username the username
     * @param sessionID the session identifier
     * @param businesID the business id
     * @param employeeID the employee id
     * @param timeOutPeriod the timeout period in ms
     * @param lastActivity the initial last activity
     */
    public UserSession(String username, String sessionID, Integer businesID, Integer employeeID, long timeOutPeriod, long lastActivity) {
        this.username = username;
        this.sessionID = sessionID;
        this.businesID = businesID;
        this.employeeID = employeeID;
        this.timeOutPeriod = timeOutPeriod;
        _lastActivity = lastActivity;
    }

    /**
     * @return the lastActivity
     */
    public long getLastActivity() {
        return _lastActivity;
    }

    /**
     * @param lastActivity the lastActivity to set
     */
    public void setLastActivity(long lastActivity) {
        _lastActivity = lastActivity;
    }
    
    /**
     * sets the last actiity to the current time
     */
    public void updateLastActivity() {
        setLastActivity(System.currentTimeMillis());
    }
    
    /**
     * Checks if session is still valid based on time
     * @return true if still active
     */
    public boolean stillActive() {
        return _lastActivity + timeOutPeriod < System.currentTimeMillis();
    }
}
