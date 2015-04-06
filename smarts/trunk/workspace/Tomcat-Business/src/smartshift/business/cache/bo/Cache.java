package smartshift.business.cache.bo;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.util.UID;
import smartshift.common.util.collections.Filter;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.collections.ROMap;
import smartshift.common.util.log4j.SmartLogger;

public class Cache {
	private static SmartLogger logger = new SmartLogger(Cache.class);
	
    private static Map<Integer, Cache> caches;
    
    private final int _rootBusID;
    
    private final BusinessDAOContext _daoContext;
    
    private final Map<UID, Reference<CachedObject>> _cached;
    
    private static final class UIDFilter implements Filter<UID> {
        private final String _type;
        
        public UIDFilter(String type) {
            _type = type;
        }
        
        @Override
        public boolean include(UID element) {
            return element.getType().equals(_type);          
        }
    }
    
    public Cache(int rootBusID) {
        _rootBusID = rootBusID;
        _cached = new HashMap<UID, Reference<CachedObject>>();
        _daoContext = BusinessDAOContext.business(_rootBusID);
    }
    
    public ROMap<UID, Reference<CachedObject>> getROCacheMap() {
        return new ROMap<UID, Reference<CachedObject>>(_cached);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends CachedObject> T getCached(UID cachedUID, Class<T> template) {
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
        if(contains(uid)) {
            _cached.remove(uid);    
        }
    }
    
    public Filter<UID> getUIDFilter(String type) {
        return new UIDFilter(type);
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
    
    public ROCollection<Role> getRoles() {
        List<Role> roles = new ArrayList<Role>();
        Filter<UID> filter = getUIDFilter(Role.TYPE_IDENTIFIER);
        for(UID uid : _cached.keySet()) {
            if(filter.include(uid))
                roles.add(getCached(uid, Role.class));
        }
        return ROCollection.wrap(roles);
    } 
    
    public Role getRole(String roleName) {
        Filter<UID> filter = getUIDFilter(Role.TYPE_IDENTIFIER);
        for(UID uid : _cached.keySet()) {
            if(filter.include(uid)) {
                Role role = getCached(uid, Role.class);
                if(role.getName().equals(roleName))
                    return role;
            }
        }
        return null;
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
        return getDAOContext().getSession();
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
    
    public static ROCollection<Integer> getCacheIDs() {
        return ROCollection.wrap(caches.keySet());
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
