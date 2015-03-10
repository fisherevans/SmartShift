package smartshift.business.cache.bo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Session;
import smartshift.business.hibernate.dao.BusinessDAOContext;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.util.UID;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.collections.ROMap;

public class Cache {
    private static Map<Integer, Cache> caches;
    
    private int _rootBusID;
    
    private BusinessDAOContext _daoContext;
    
    private Map<UID, WeakReference<CachedObject>> _cached;
    
    public Cache(int rootBusID) {
        _rootBusID = rootBusID;
        _cached = new HashMap<UID, WeakReference<CachedObject>>();
        _daoContext = BusinessDAOContext.business(_rootBusID);
    }
    
    public ROMap<UID, WeakReference<CachedObject>> getROCacheMap() {
        return new ROMap<UID, WeakReference<CachedObject>>(_cached);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends CachedObject> T getCached(UID cachedUID, T template) {
        if(!_cached.containsKey(cachedUID))
            return null;
        try {
            return (T) _cached.get(cachedUID).get();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean contains(UID cachedUID) {
        return _cached.containsKey(cachedUID);
    }
    
    public void cache(UID uid, CachedObject toCache) {
        _cached.put(uid, new WeakReference<CachedObject>(toCache));
    }
    
    public void decache(UID uid) {
        if(contains(uid))
            _cached.remove(uid);
    }
    
    public Employee getEmployee(int empID) {
        UID uid = new UID(Employee.TYPE_IDENTIFIER, empID);
        if(!_cached.containsKey(uid))
            return null;
        return (Employee) _cached.get(uid).get();
    }
    
    public Role getRole(int roleID) {
        UID uid = new UID(Role.TYPE_IDENTIFIER, roleID);
        if(!_cached.containsKey(uid))
            return null;
        return (Role) _cached.get(uid).get();
    }
    
    public Group getGroup(int groupID) {
        UID uid = new UID(Group.TYPE_IDENTIFIER, groupID);
        if(!_cached.containsKey(uid))
            return null;
        return (Group) _cached.get(uid).get();
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
        for(WeakReference<CachedObject> co : _cached.values()) {
            co.get().save();
        }
        clean();
    }
    
    public void clean() {
        _cached.clear();
    }
    
    private void loadAllData() {
        ROCollection<EmployeeModel> employees = getDAOContext().dao(EmployeeDAO.class).list().execute();
        for(EmployeeModel employee:employees)
            Employee.load(this, employee.getId());

        ROCollection<GroupModel> groups = getDAOContext().dao(GroupDAO.class).list().execute();
        for(GroupModel group:groups)
            Group.load(this, group.getId());
        
    }
    
    public static Cache getCache(Integer busID) {
        if(caches == null)
            caches = new HashMap<Integer, Cache>();
        if(!caches.containsKey(busID)) {
            Cache newCache = new Cache(busID);
            caches.put(busID, newCache);
            newCache.loadAllData();
        }
        return caches.get(busID);
    }
}
