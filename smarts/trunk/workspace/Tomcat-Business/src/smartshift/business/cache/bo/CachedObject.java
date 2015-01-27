package smartshift.business.cache.bo;

public abstract class CachedObject {
    private Cache _cache;
    
    public CachedObject(Cache cache) {
        _cache = cache;
    }
}
