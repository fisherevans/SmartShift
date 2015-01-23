package smartshift.common.cache.bo.business;

import java.util.HashMap;
import java.util.Map;
import smartshift.common.cache.bo.accounts.Business;

public class BusinessCache {
    private Business _root;
    
    private Map<Integer, Employee> _employees;
    private Map<Integer, Role> _roles;
    private Map<Integer, Group> _groups;
    
    public BusinessCache(Business root) {
        _root = root;
        _employees = new HashMap<Integer, Employee>();
        _roles = new HashMap<Integer, Role>();
        _groups = new HashMap<Integer, Group>();
    }
    
    public Employee getEmployee(int empID) {
        if(!_employees.containsKey(empID))
            return null;
        return _employees.get(empID);
    }
    
    public void addEmployee(int empID, Employee emp) {
        _employees.put(empID, emp);
    }
    
    public Role getRole(int roleID) {
        if(!_roles.containsKey(roleID))
            return null;
        return _roles.get(roleID);
    }
    
    public void addRole(int roleID, Role role) {
        _roles.put(roleID, role);
    }
    
    public Group getGroup(int groupID) {
        if(!_groups.containsKey(groupID))
            return null;
        return _groups.get(groupID);
    } 
    
    public void addGroup(int groupID, Group group) {
        _groups.put(groupID, group);
    }

    public Business getBusiness() {
        return _root;
    }

    public void save() {
        for(Group g : _groups.values())
            g.save();
        for(Employee e : _employees.values())
            e.save();
        for(Role r : _roles.values())
            r.save();      
    }
}
