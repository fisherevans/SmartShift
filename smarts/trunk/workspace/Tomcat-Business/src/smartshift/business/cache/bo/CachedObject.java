package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.BaseBusinessDAO;
import smartshift.common.util.Identifiable;
import smartshift.common.util.UID;
import smartshift.common.util.hibernate.Stored;

public abstract class CachedObject implements Identifiable, Stored{
    private Cache _cache;
    private int _id;
    
    public CachedObject(Cache cache) {
        this(cache, -1);
    }
    
    public CachedObject(Cache cache, int id) {
        _cache = cache;
        _id = id;
    }

    public Cache getCache() {
        return _cache;
    }
    
    @Override
    public UID getUID() {
        return new UID(this);
    }
    
    public int getID() {
        return _id;
    }
    
    protected void setID(int id) {
        _id = id;
    }
    
    public <T extends BaseBusinessDAO> T getDAO(Class<T> clazz) {
        return getCache().getDAOContext().dao(clazz);
    }
    
    public void save() {
        saveRelationships();
    }
    
    protected void finalize() throws Throwable {
        _cache.decache(getUID());
        super.finalize();
    }
}
