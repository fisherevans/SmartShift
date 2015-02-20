package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.BaseBusinessDAO;
import smartshift.common.util.Identifiable;
import smartshift.common.util.UID;
import smartshift.common.util.hibernate.Stored;

public abstract class CachedObject implements Identifiable, Stored{
    private Cache _cache;
    
    public CachedObject(Cache cache) {
        _cache = cache;
    }
    
    public Cache getCache() {
        return _cache;
    }
    
    @Override
    public UID getUID() {
        return new UID(this);
    }
    
    public <T extends BaseBusinessDAO> T getDAO(Class<T> clazz) {
        return getCache().getDAOContext().dao(clazz);
    }
}
