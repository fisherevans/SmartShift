package smartshift.common.cache.bo.business;

import smartshift.common.cache.bo.accounts.Business;

public abstract class CachedObject {
    private Cache _cache;
    
    public CachedObject(Cache cache) {
        _cache = cache;
    }
}
