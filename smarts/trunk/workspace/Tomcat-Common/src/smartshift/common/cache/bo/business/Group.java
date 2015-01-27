package smartshift.common.cache.bo.business;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import smartshift.common.cache.bo.accounts.Business;
import smartshift.common.hibernate.model.business.GroupModel;
import smartshift.common.util.hibernate.Stored;

public class Group extends CachedObject implements Stored{
    
    private String _name;
    private Map<Role, Set<Employee>> _employees;
    
    private GroupModel _model;

    public Group(Business business) {
        super(business);
        _employees = new HashMap<Role, Set<Employee>>();
        _employees.put(Role.getBasicRole(business, this), new HashSet<Employee>());
    }
    
    private Group(BusinessCache cache, GroupModel model) {
        this(cache.getBusiness());
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
    
    public static Group getGroup(BusinessCache cache, GroupModel model) {
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
