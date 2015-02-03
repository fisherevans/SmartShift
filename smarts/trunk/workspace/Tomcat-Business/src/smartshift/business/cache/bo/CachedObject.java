package smartshift.business.cache.bo;

import smartshift.common.util.Identifiable;
import smartshift.common.util.UID;
import smartshift.common.util.hibernate.Stored;

public abstract class CachedObject implements Identifiable, Stored{
    private Cache _cache;
    
    public CachedObject(Cache cache) {
        _cache = cache;
    }
    
    public UID getUID() {
        return new UID(this);
    }
}
