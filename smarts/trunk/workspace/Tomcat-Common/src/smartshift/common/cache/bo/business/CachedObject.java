package smartshift.common.cache.bo.business;

import smartshift.common.cache.bo.accounts.Business;

public abstract class CachedObject {
    private BusinessCache _cache;
    
    public CachedObject(Business business) {
        _cache = business.getCache();
    }
}
