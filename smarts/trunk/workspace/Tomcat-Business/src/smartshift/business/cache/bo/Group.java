package smartshift.business.cache.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.dao.RoleDAO;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.util.UID;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

public class Group extends CachedObject {
    public static final String TYPE_IDENTIFIER = "G";
    
    private static final SmartLogger logger = new SmartLogger(Group.class);
    
    private String _name;
    private Group _parent;
    private Boolean _active;
    private final Set<Group> _children;
    private final Map<Role, Set<Employee>> _employees;
    
    private GroupModel _model;

    private Group(Cache cache, String name) {
        super(cache);
        _name = name;
        _active = true;
        _children = new HashSet<Group>();
        _employees = new HashMap<Role, Set<Employee>>();
        _employees.put(Role.getBasicRole(cache, this), new HashSet<Employee>());
    }
    
    private Group(Cache cache, int id) {
        super(cache, id);
        _children = new HashSet<Group>();
        _employees = new HashMap<Role, Set<Employee>>();
        _employees.put(Role.getBasicRole(cache, this), new HashSet<Employee>());
    }
    
    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setParent(Group parent) {
        _parent = parent;
    }
    
    public Group getParent() {
    	return _parent;
    }

    public void setActive(Boolean active) {
        _active = active;
    }
    
    public Boolean getActive() {
		return _active;
	}
    
	// --- Child Groups
	
    protected boolean childAdded(Group grp) {
        return _children.add(grp);
    }

    public ROCollection<Group> getChildGroups() {
        return ROCollection.wrap(_children);
    }
    
    protected boolean childRemoved(Group grp) {
        return _children.remove(grp);
    }
    
    // --- Roles

    public void addRole(Role role) {
        if(!hasRole(role))
            _employees.put(role, new HashSet<Employee>());
    }
    
    public ROCollection<Role> getRoles() {
        return ROCollection.wrap(_employees.keySet());
    }
    
    public boolean hasRole(Role role) {
        return _employees.containsKey(role);
    }
    
    public void removeRole(Role role) {
        Set<Employee> roleEmployees = _employees.get(role);
        if(roleEmployees != null) {
            for(Employee employee:roleEmployees)
                removeRoleEmployee(role, employee);
            _employees.remove(role);
        }
        
    }
    
    // --- Role Employees
    
    public void addRoleEmployee(Role role, Employee employee) {
        addRole(role);
        _employees.get(role).add(employee);
        employee.groupRoleAdded(this, role);
    }
    
    public ROCollection<Employee> getRoleEmployees(Role role) {
        Set<Employee> roleEmployees = _employees.get(role);
        return ROCollection.wrap(roleEmployees == null ? new ArrayList() : roleEmployees);
    }
    
    public void removeRoleEmployee(Role role, Employee employee) {
        Set<Employee> roleEmployees = _employees.get(role);
        if(roleEmployees != null) {
            roleEmployees.remove(employee);
            employee.groupRoleRemoved(this, role);
        }
    }
    
    // --- Employees
    
    public void removeEmployee(Employee employee) {
        for(Role role:_employees.keySet()) {
            removeRoleEmployee(role, employee);
            if(_employees.get(role).size() == 0)
                removeRole(role);
        }
    }
    
    // --- Misc

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setName(_name);
                _model.setActive(_active);
                _model.setParentID(_parent == null ? null : _parent.getID());
                getDAO(GroupDAO.class).update(_model);
            } else {
                _model = getDAO(GroupDAO.class).add(_name, null).execute();
                setID(_model.getId());
            }
        } catch (HibernateException e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void loadAllChildren() {
        try {
             // load child groups
             for(GroupModel gm : getDAO(GroupDAO.class).listChildGroups(getID()).execute()){
                 int grpID = gm.getId();
                 Group grp = Group.load(getCache(), grpID);
                 _children.add(grp);
             }
            
            for(RoleModel rm : getDAO(RoleDAO.class).listByGroup(getID()).execute()){
                int roleID = rm.getId();
                Role role = Role.load(getCache(), roleID);
                _employees.put(role, new HashSet<Employee>());
                for(EmployeeModel em : getDAO(EmployeeDAO.class).listByGroupRole(getID(), roleID).execute()){
                    _employees.get(role).add(Employee.load(getCache(), em.getId()));
                }
            }
        } catch(Exception e) {
            logger.error("Failed to load children", e);
        }     
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    public static Group load(Cache cache, int grpID) {
    	UID uid = new UID(TYPE_IDENTIFIER, grpID);
        if(cache.contains(uid)) {
            logger.debug("Cache has group: " + grpID);
            return cache.getGroup(grpID); 
        } else {
            logger.debug("Cache does not have group: " + grpID);
            Group group = new Group(cache, grpID);
            cache.cache(uid, group);
            group.loadAllChildren();
            group.init();
            return group;
        }
    }
    
    public void init() {
        GroupModel model = getCache().getDAOContext().dao(GroupDAO.class).uniqueByID(getID()).execute();
        _name = model.getName();
        if(model.getParentID() == null)
            _parent = null;
        else
            _parent = Group.load(getCache(), model.getParentID());
        _model = model;
    }
     
    /**
     * create a new group
     * @param businessID the id of the Business that the group belongs to
     * @param name the name of the group
     * @param parent null if no parent
     * @return the new group
     */
    public static Group create(int businessID, String name, Group parent) {
        Cache cache = Cache.getCache(businessID);
        Group grp = new Group(cache, name);
        grp.save();
        cache.cache(new UID(grp), grp);
        return grp;
    }
    
    public void delete() {
        // TODO drew - flag this group as inactive - as long as it's not root
        logger.error("Hit a non-implemeneted block! delete()");
        throw new RuntimeException("To implement!");
    }

    public boolean isValidParentOf(Group group) {
        logger.error("Hit a non-implemeneted block! isValidParentOf()");
        throw new RuntimeException("To implement!");
        // TODO drew - true if the passed group is a valid child of this group. Looking to avoid prevent infinite loops and what so
    }
}
