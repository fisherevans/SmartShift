package smartshift.common.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * a wrapper for executing a transaction with a session
 */
public class DBAction {
    private static final SmartLogger logger = new SmartLogger(DBAction.class);
    
    private final Session _session;
    private final Transaction _transaction;
    private boolean _complete = false;
    
    /**
     * Initializes the object. creates a transaction
     * @param session the session
     */
    public DBAction(Session session) {
        _session = session;
        if(_session == null)
            throw new RuntimeException("Obtained a null session!");
        _transaction = session.beginTransaction();
        if(_transaction == null)
            throw new RuntimeException("Failed to create a transaction!");
        logger.trace("Session and Action stored");
    }
    
    /**
     * @return this action's session
     */
    public Session session() {
        return _session;
    }
    
    /**
     * @return this action's transaction
     */
    public Transaction transaction() {
        return _transaction;
    }
    
    /**
     * @return true if this action has not been closed
     */
    public boolean isComplete() {
        return _complete;
    }
    
    /**
     * if not completed - commits the transaction then calls close
     */
    public void commit() {
        if(_complete)
            return;
        logger.trace("Commiting transaction");
        _transaction.commit();
        close(true);
    }

    /**
     * if not completed - rolls back the transaction then calls close
     */
    public void rolback() {
        if(_complete)
            return;
        logger.trace("Rolling back transaction");
        _transaction.rollback();
        close(false);
    }

    /**
     * if not completed - flushes and closes the session. sets this action state to complete.
     * @param doFlush Pass true to flush the session before closing. DO NOT flush the session if an exception occurred.
     */
    public void close(boolean doFlush) {
        if(_complete)
            return;
        _complete = true;
        logger.trace("Flushing and Closing the session");
        if(doFlush)
            _session.flush();
        _session.close();
    }
}
