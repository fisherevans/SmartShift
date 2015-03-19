package smartshift.business.cache.bo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import smartshift.business.hibernate.dao.BusinessDAOContext;
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
    
    private int _rootBusID;
    
    private BusinessDAOContext _daoContext;
    
    private Map<UID, WeakReference<CachedObject>> _cached;
    
    private static final class UIDFilter implements Filter<UID> {
        private String _type;
        
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
        _cached = new HashMap<UID, WeakReference<CachedObject>>();
        _daoContext = BusinessDAOContext.business(_rootBusID);
    }
    
    public ROMap<UID, WeakReference<CachedObject>> getROCacheMap() {
        return new ROMap<UID, WeakReference<CachedObject>>(_cached);
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
            _cached.get(uid).get().save();
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
    
    public static void saveAllCaches() {
        for(Cache cache : caches.values()) {
            cache.save();
        }
    }

	public void deleteEmployee(Employee employee) {
		logger.error("Hit a non-implemeneted block! deleteEmployee()");
		throw new RuntimeException("To implement!");
		// TODO Drew this needs to set the flag, save to the DB and remove any relations.
	}

    public void deleteGroup(Group group) {
        logger.error("Hit a non-implemeneted block! deleteGroup()");
        throw new RuntimeException("To implement!");
        // TODO drew - flag this group as inactive - as long as it's not root
    }

    public Role renameGroupRole(Group group, Role role, String roleName) {
        logger.error("Hit a non-implemeneted block! deleteGroup()");
        throw new RuntimeException("To implement!");
        // TODO drew - renames a role. check to see if any other groups use it, 
        // if they do need to make a new role and refactor the relationships, if not just rename
    }

    public void removeGroupRole(Group group, Role role) {
        logger.error("Hit a non-implemeneted block! deleteGroup()");
        throw new RuntimeException("To implement!");
        // TODO drew - removes role from group. need to remove employees from group if it was their only role in it
    }

    public void removeGroupEmployee(Group group, Employee employee) {
        logger.error("Hit a non-implemeneted block! deleteGroup()");
        throw new RuntimeException("To implement!");
        // TODO drew - remove employee from group and all roles inside group
    }

    public void removeGroupRoleEmployee(Group group, Role role, Employee employee) {
        logger.error("Hit a non-implemeneted block! deleteGroup()");
        throw new RuntimeException("To implement!");
        // TODO drew - remove employee from a role in a group. from the whole group if its their last role
    }
}
