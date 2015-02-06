package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.util.UID;
import smartshift.common.util.hibernate.Stored;

public class Group extends CachedObject {
    public static final String TYPE_IDENTIFIER = "G";
    
    private String _name;
    private Map<Role, Set<Employee>> _employees;
    
    private GroupModel _model;

    public Group(Cache cache, String name) {
        super(cache);
        _name = name;
        _employees = new HashMap<Role, Set<Employee>>();
        _employees.put(Role.getBasicRole(cache, this), new HashSet<Employee>());
    }
    
    private Group(Cache cache, GroupModel model) {
        this(cache, model.getName());
        _model = model;
    }
    
    public String getName() {
        return _name;
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
    
    public static Group getGroup(Cache cache, int groupID) {
        Group group = cache.getGroup(groupID);
        if(group == null)
            group = new Group(cache, cache.getDAOContext().dao(GroupDAO.class).getGroupById(groupID));
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

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public int getID() {
        if(_model == null)
            return -1;
        return _model.getId();
    }
    
    public static Group load(Cache cache, int grpID) {
        if(cache.contains(new UID(TYPE_IDENTIFIER, grpID)))
            return cache.getGroup(grpID);
        return null;
    }
    
    public static Group createNewGroup() {
        return null;
    }
}
