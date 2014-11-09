package smartshift.common.hibernate;

public class DBException extends Exception {
    private DBError _error;
    
    public DBException(DBError error) {
        this(error, null);
    }
    
    public DBException(DBError error, String message) {
        super(message);
        _error = error;
    }

    public DBError getError() {
        return _error;
    }

    public void setError(DBError error) {
        _error = error;
    }
}
