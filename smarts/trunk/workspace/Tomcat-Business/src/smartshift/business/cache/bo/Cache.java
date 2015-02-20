package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import smartshift.business.hibernate.dao.BusinessDAOContext;
import smartshift.common.util.UID;

public class Cache {
    private static Map<Integer, Cache> caches;
    
    private int _rootBusID;
    
    private BusinessDAOContext _daoContext;
    
    private Map<UID, CachedObject> _cached;
    
    public Cache(int rootBusID) {
        _rootBusID = rootBusID;
        _cached = new HashMap<UID, CachedObject>();
        _daoContext = BusinessDAOContext.business(_rootBusID);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends CachedObject> T getCached(UID cachedUID, T template) {
        if(!_cached.containsKey(cachedUID))
            return null;
        try {
            return (T) _cached.get(cachedUID);
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean contains(UID cachedUID) {
        return _cached.containsKey(cachedUID);
    }
    
    public void cache(UID uid, CachedObject toCache) {
        if(!contains(uid))
            _cached.put(uid, toCache);
    }
    
    public void decache(UID uid) {
        if(contains(uid))
            _cached.remove(uid);
    }
    
    public Employee getEmployee(int empID) {
        UID uid = new UID(Employee.TYPE_IDENTIFIER, empID);
        if(!_cached.containsKey(uid))
            return null;
        return (Employee) _cached.get(uid);
    }
    
    public Role getRole(int roleID) {
        UID uid = new UID(Role.TYPE_IDENTIFIER, roleID);
        if(!_cached.containsKey(uid))
            return null;
        return (Role) _cached.get(uid);
    }
    
    public Group getGroup(int groupID) {
        UID uid = new UID(Group.TYPE_IDENTIFIER, groupID);
        if(!_cached.containsKey(uid))
            return null;
        return (Group) _cached.get(uid);
    } 

    public int getBusinessID() {
        return _rootBusID;
    }
    
    public BusinessDAOContext getDAOContext() {
        return _daoContext;
    }
    
    public Session getBusinessSession() {
        return getDAOContext().getBusinessSession();
    }

    public void save() {
        for(CachedObject co : _cached.values()) {
            co.save();
        }
        clean();
    }
    
    public void clean() {
        _cached.clear();
    }
    
    public static Cache getCache(Integer busID) {
        if(caches == null)
            caches = new HashMap<Integer, Cache>();
        if(!caches.containsKey(busID))
            caches.put(busID, new Cache(busID));
        return caches.get(busID);
    }
}
