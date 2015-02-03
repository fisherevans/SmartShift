package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.util.hibernate.Stored;

public class Group extends CachedObject implements Stored{
    
    private String _name;
    private Map<Role, Set<Employee>> _employees;
    
    private GroupModel _model;

    public Group(Cache cache) {
        super(cache);
        _employees = new HashMap<Role, Set<Employee>>();
        _employees.put(Role.getBasicRole(cache, this), new HashSet<Employee>());
    }
    
    private Group(Cache cache, GroupModel model) {
        this(cache);
        _model = model;
    }
    
    public boolean hasRole(Role role) {
        return _employees.containsKey(role);
    }

    public void addRole(Role role) {
        if(!hasRole(role))
            _employees.put(role, new HashSet<Employee>());
    }
    
    public void addEmployee(Employee employee, Role role) {
        addRole(role);
        if(!_employees.get(role).contains(employee))
            _employees.get(role).add(employee);
    }
    
    public static Group getGroup(Cache cache, GroupModel model) {
        Group group = cache.getGroup(model.getId());
        if(group == null)
            group = new Group(cache, model);
        return group;
    }

    public GroupModel getModel() {
        return _model;
    }

    public void save() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }

}