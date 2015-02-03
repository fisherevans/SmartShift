package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.Map;
import smartshift.common.util.UID;

public class Cache {
    private static Map<Integer, Cache> caches;
    
    private int _rootBusID;
    
    private Map<UID, CachedObject> _cached;
    
    public Cache(int rootBusID) {
        _rootBusID = rootBusID;
        _cached = new HashMap<UID, CachedObject>();
    }
    
    public Employee getEmployee(int empID) {
        UID uid = new UID("E", empID);
        if(!_cached.containsKey(uid))
            return null;
        return (Employee) _cached.get(uid);
    }
    
    public void addEmployee(Employee emp) {
        _cached.put(emp.getUID(), emp);
    }
    
    public Role getRole(int roleID) {
        UID uid = new UID("R", roleID);
        if(!_cached.containsKey(uid))
            return null;
        return (Role) _cached.get(uid);
    }
    
    public void addRole(Role role) {
        _cached.put(role.getUID(), role);
    }
    
    public Group getGroup(int groupID) {
        UID uid = new UID("G", groupID);
        if(!_cached.containsKey(uid))
            return null;
        return (Group) _cached.get(uid);
    } 
    
    public void addGroup(Group group) {
        _cached.put(group.getUID(), group);
    }

    public int getBusinessID() {
        return _rootBusID;
    }

    public void save() {
        for(CachedObject co : _cached.values()) {
            co.save();
        }
    }
    
    public static Cache getCache(Integer busID) {
        if(caches == null)
            caches = new HashMap<Integer, Cache>();
        if(!caches.containsKey(busID))
            caches.put(busID, new Cache(busID));
        return caches.get(busID);
    }
}
