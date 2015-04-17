package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.BaseBusinessDAO;
import smartshift.common.util.Identifiable;
import smartshift.common.util.UID;
import smartshift.common.util.hibernate.Stored;

/**
 * an object which is cached
 * @author drew
 */
public abstract class CachedObject implements Identifiable, Stored{
    private Cache _cache;
    private int _id;
    private boolean _initialized;
    
    /**
     * constructor for a new cached object with no id yet determined
     * @param cache the cache to add the object to
     */
    public CachedObject(Cache cache) {
        this(cache, -1);
        _initialized = true;
    }
    
    /**
     * constructor for a new cached object with an id already determined
     * @param cache the cache to add the object to
     * @param id the id of the object
     */
    public CachedObject(Cache cache, int id) {
        _cache = cache;
        _id = id;
        _initialized = false;
    }
    
    /**
     * initialize the fields of this skeleton object
     * @param requester the employee the object is being initialized for
     */
    public synchronized void initialize(Employee requester) {
        init();
        _initialized = true;
    }
    
    /**
     * do the work for initializing object fields
     */
    public abstract void init();

    /**
     * @return true if this object is initialized 
     */
    public boolean isInitialized() {
        return _initialized;
    }

    /**
     * @return the cache that holds this
     */
    public Cache getCache() {
        return _cache;
    }
    
    /**
     * @see smartshift.common.util.Identifiable#getUID()
     */
    @Override
    public UID getUID() {
        return new UID(this);
    }
    
    /**
     * @see smartshift.common.util.Identifiable#getID()
     */
    public int getID() {
        return _id;
    }
    
    /**
     * set the id of the cached object
     * @param id the id to set
     */
    protected synchronized void setID(int id) {
        _id = id;
    }
    
    /**
     * get a dao for the given dao class from this object's cache
     * @param <T> the dao type
     * @param clazz the dao class
     * @return the dao
     */
    @SuppressWarnings("rawtypes")
    public <T extends BaseBusinessDAO> T getDAO(Class<T> clazz) {
        return getCache().getDAOContext().dao(clazz);
    }
    
    /**
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable {
        _cache.decache(getUID());
        super.finalize();
    }
}
