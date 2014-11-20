package smartshift.common.hibernate;

/**
 * A exception that is thrown when using the GenericHibernateUtil
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class DBException extends Exception {
    private static final long serialVersionUID = -6042776976252546663L;
    
    /**
     * The DB error causing this exception
     */
    private DBError _error;
    
    /**
     * Creates the exception with a given error
     * @param error what caused the error
     */
    public DBException(DBError error) {
        this(error, null);
    }
    
    /**
     * Creates the exception with a given error and message
     * @param error what caused the error
     * @param message a related message for debugging
     */
    public DBException(DBError error, String message) {
        super(message);
        _error = error;
    }

    /**
     * @return the error
     */
    public DBError getError() {
        return _error;
    }

    /**
     * @param error the error to set
     */
    public void setError(DBError error) {
        _error = error;
    }
}
