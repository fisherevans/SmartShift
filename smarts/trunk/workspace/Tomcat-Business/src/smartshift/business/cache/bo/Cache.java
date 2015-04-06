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

/**
 * a cache in which to hold all the cached objects in memory for any business context
 * @author drew
 */
public class Cache {
    /** all the caches, mapped by business id */
    private static Map<Integer, Cache> caches;
    
    private final int _rootBusID;
    private final BusinessDAOContext _daoContext;
    private final Map<UID, Reference<CachedObject>> _cached;
    
    /**
     * a filter to find only objects whose uid matches a given type code
     * @author drew
     */
    private static final class UIDFilter implements Filter<UID> {
        private final String _type;
        
        /**
         * constructor for a new filter
         * @param type the type to filter by
         */
        public UIDFilter(String type) {
            _type = type;
        }
        
        /**
         * @see smartshift.common.util.collections.Filter#include(java.lang.Object)
         */
        @Override
        public boolean include(UID element) {
            return element.getType().equals(_type);          
        }
    }
    
    /**
     * constructor for a new cache
     * @param rootBusID the id of the business that the cache is for
     */
    public Cache(int rootBusID) {
        _rootBusID = rootBusID;
        _cached = new HashMap<UID, Reference<CachedObject>>();
        _daoContext = BusinessDAOContext.business(_rootBusID);
    }
    
    /**
     * @return a read only map of all data cached by this
     */
    public ROMap<UID, Reference<CachedObject>> getROCacheMap() {
        return new ROMap<UID, Reference<CachedObject>>(_cached);
    }
    
    /**
     * get an object from the cache that matches the given UID
     * @param <T> the type of the requested object
     * @param cachedUID the UID identifying the requested object
     * @param template the class of the requested object
     * @return the object identified by the provided UID, or null
     *   if it doesn't exist in the cache, or isn't of the desired type
     */
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
    
    /**
     * check if the cache contains an object with the given UID
     * @param cachedUID the UID to check for
     * @return true if the cache contains the object
     */
    public boolean contains(UID cachedUID) {
        return _cached.containsKey(cachedUID);
    }
    
    /**
     * add an object to the cache
     * @param uid the uid which identifies the object to add
     * @param toCache the object to cache
     */
    public void cache(UID uid, CachedObject toCache) {
        _cached.put(uid, new WeakReference<CachedObject>(toCache));
    }
    
    /**
     * remove an object from the cache
     * @param uid the uid which identifies the object to remove
     */
    public void decache(UID uid) {
        if(contains(uid)) {
            _cached.remove(uid);    
        }
    }
    
    /**
     * get a filter over UIDs for the given type code
     * @param type the type code to filter on
     * @return the filter
     */
    public static Filter<UID> getUIDFilter(String type) {
        return new UIDFilter(type);
    }
    
    /**
     * get an employee from the cache
     * @param empID the employee's id
     * @return the employee, or null if it isn't cached
     */
    public Employee getEmployee(int empID) {
        UID uid = new UID(Employee.TYPE_IDENTIFIER, empID);
        if(!_cached.containsKey(uid))
            return null;
        return (Employee) _cached.get(uid).get();
    }
    
    /**
     * get a role from the cache
     * @param roleID the role's id
     * @return the role, or null if it isn't cached
     */
    public Role getRole(int roleID) {
        UID uid = new UID(Role.TYPE_IDENTIFIER, roleID);
        if(!_cached.containsKey(uid))
            return null;
        return (Role) _cached.get(uid).get();
    }
    
    /**
     * @return all roles in the cache
     */
    public ROCollection<Role> getRoles() {
        List<Role> roles = new ArrayList<Role>();
        Filter<UID> filter = getUIDFilter(Role.TYPE_IDENTIFIER);
        for(UID uid : _cached.keySet()) {
            if(filter.include(uid))
                roles.add(getCached(uid, Role.class));
        }
        return ROCollection.wrap(roles);
    } 
    
    /**
     * get a role from the cache from a name
     * @param roleName the name of the role to get
     * @return the role, or null if it isn't cached
     */
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
    
    /**
     * get a group from the cache
     * @param groupID the group's id
     * @return the group, or null if it isn't cached
     */
    public Group getGroup(int groupID) {
        UID uid = new UID(Group.TYPE_IDENTIFIER, groupID);
        if(!_cached.containsKey(uid))
            return null;
        return (Group) _cached.get(uid).get();
    } 

    /**
     * @return id of the business that the cache is for
     */
    public int getBusinessID() {
        return _rootBusID;
    }
    
    /**
     * @return the cache's dao context
     */
    public BusinessDAOContext getDAOContext() {
        return _daoContext;
    }
    
    /**
     * @return the dao context's session
     */
    public Session getBusinessSession() {
        return getDAOContext().getSession();
    }
    
    /**
     * empty the cache
     */
    public void clean() {
        _cached.clear();
    }
    
    /**
     * fill the cache with Groups, Roles, and Employees
     */
    private void loadAllData() {
        ROCollection<EmployeeModel> employees = getDAOContext().dao(EmployeeDAO.class).list().execute();
        for(EmployeeModel employee:employees)
            Employee.load(this, employee.getId());

        ROCollection<GroupModel> groups = getDAOContext().dao(GroupDAO.class).list().execute();
        for(GroupModel group:groups)
            Group.load(this, group.getId());
    }
    
    /**
     * @return a list of all ids for businesses with active caches
     */
    public static ROCollection<Integer> getCacheIDs() {
        return ROCollection.wrap(caches.keySet());
    }
    
    /**
     * get a cache for the given business id
     * @param busID the business id to get a cache for
     * @return the cache
     */
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
