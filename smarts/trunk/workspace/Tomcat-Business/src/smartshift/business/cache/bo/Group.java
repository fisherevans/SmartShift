package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.dao.GroupRoleCapabilityDAO;
import smartshift.business.hibernate.dao.GroupRoleDAO;
import smartshift.business.hibernate.dao.GroupRoleEmployeeDAO;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.hibernate.model.GroupRoleCapabilityModel;
import smartshift.business.hibernate.model.GroupRoleModel;
import smartshift.common.util.UID;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

public class Group extends CachedObject {
    public static final String TYPE_IDENTIFIER = "G";
    
    public static final Integer MANAGER_CAPABILITY = 999;
    
    private static final SmartLogger logger = new SmartLogger(Group.class);
    
    private String _name;
    private Group _parent;
    private Boolean _active;
    private final Set<Group> _children;
    private final Map<Role, GroupRole> _employees;
    
    private GroupModel _model;

    private Group(Cache cache, String name) {
        super(cache);
        _name = name;
        _active = true;
        _children = new HashSet<Group>();
        _employees = new HashMap<Role, GroupRole>();
        addRole(Role.getBasicRole(cache, this));
    }
    
    private Group(Cache cache, int id) {
        super(cache, id);
        _children = new HashSet<Group>();
        _employees = new HashMap<Role, GroupRole>();
        addRole(Role.getBasicRole(cache, this));
    }
    
    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setParent(Group parent) {
        if(_parent != null)
            _parent.childRemoved(this);
        _parent = parent;
        if(_parent != null)
            _parent.childAdded(this);
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
            _employees.put(role, new GroupRole());
    }
    
    public ROCollection<Role> getRoles() {
        return ROCollection.wrap(_employees.keySet());
    }
    
    public boolean hasRole(Role role) {
        return _employees.containsKey(role);
    }
    
    public void removeRole(Role role) {
        ROCollection<Employee> roleEmployees = _employees.get(role).getEmployees();
        if(roleEmployees != null) {
            for(Employee employee:roleEmployees)
                removeRoleEmployee(role, employee);
            _employees.remove(role);
        }
        
    }
    
    // --- Role Capabilities
    
    public void addRoleCapability(Role role, Integer capabilityID) {
        if(!hasRole(role))
            throw new RuntimeException(String.format("Group:%d does not have the Role:%d to set capability for.", getID(), role.getID()));
        _employees.get(role).addCapability(capabilityID);
        role.capabilityAdded(this, capabilityID);
    }
    
    public boolean hasRoleCapability(Role role, Integer capabilityID) {
        if(!hasRole(role))
            throw new RuntimeException(String.format("Group:%d does not have the Role:%d to get capability from.", getID(), role.getID()));
        return _employees.get(role).hasCapability(capabilityID);
    }
    
    // --- Role Employees
    
    public void addRoleEmployee(Role role, Employee employee) {
        if(!hasRole(role))
            throw new RuntimeException(String.format("Group:%d does not have the Role:%d to add employee too.", getID(), role.getID()));
        _employees.get(role).addEmployee(employee);
        employee.groupRoleAdded(this, role);
    }
    
    public ROCollection<Employee> getRoleEmployees(Role role) {
        return _employees.get(role).getEmployees();
    }
    
    public void removeRoleEmployee(Role role, Employee employee) {
        GroupRole groupRole = _employees.get(role);
        if(groupRole != null) {
            groupRole.removeEmployee(employee);
            employee.groupRoleRemoved(this, role);
        }
    }
    
    // --- Employees
    
    public void removeEmployee(Employee employee) {
        if(equals(employee.getHomeGroup()))
            throw new RuntimeException(String.format("Employee:%d has a parent group of %d, so cannot remove from %d",
                    employee.getID(), employee.getHomeGroup().getID(), this.getID()));
        for(Role role:_employees.keySet()) {
            removeRoleEmployee(role, employee);
        }
        employee.groupRemoved(this);
    }
    
    // --- Misc
    
    public void delete() {
        // TODO drew - flag this group as inactive - as long as it's not root
        // Need to worry about employees with this as home group
        logger.error("Hit a non-implemeneted block! delete()");
        throw new RuntimeException("To implement!");
    }

    public boolean isValidParentOf(Group group) {
        logger.error("Hit a non-implemeneted block! isValidParentOf()");
        throw new RuntimeException("To implement!");
        // TODO drew - true if the passed group is a valid child of this group. Looking to avoid prevent infinite loops and what so
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setName(_name);
                _model.setActive(_active);
                _model.setParentID(_parent == null ? null : _parent.getID());
                getDAO(GroupDAO.class).update(_model);
                super.save();
            } else {
                _model = getDAO(GroupDAO.class).add(_name, null).execute();
                setID(_model.getId());
                super.save();
            }
        } catch (HibernateException e) {
            logger.debug(e.getStackTrace());
        }
    }
    
    @Override
    public void saveRelationships() {
        // save group roles
        for(Role r : _employees.keySet()) {
            if(getDAO(GroupRoleDAO.class).linkCount(getID(), r.getID()).execute() < 1)
                getDAO(GroupRoleDAO.class).link(getID(), r.getID()).execute();
            int groupRole = getDAO(GroupRoleDAO.class).uniqueByGroupRole(getID(), r.getID()).execute().getId();
            for(Employee e : _employees.get(r).getEmployees()) {
                if(getDAO(GroupRoleEmployeeDAO.class).linkCount(groupRole, e.getID()).execute() < 1)
                    getDAO(GroupRoleEmployeeDAO.class).link(groupRole, e.getID());
            }
            for(Integer c : _employees.get(r).getCapabilities()) {
                if(getDAO(GroupRoleCapabilityDAO.class).linkCount(groupRole, c).execute() < 1)
                    getDAO(GroupRoleCapabilityDAO.class).link(groupRole, c);
            }
        }
    }

    @Override
    public void loadAllChildren() {
        try {
             // load child groups
             for(GroupModel gm : getDAO(GroupDAO.class).listChildGroups(getID()).execute())
                 Group.load(getCache(), gm.getId()).setParent(this);
             
             for(GroupRoleModel gr:getDAO(GroupRoleDAO.class).listByGroup(getID()).execute()) {
                 Role role = Role.load(getCache(), gr.getRoleID());
                 addRole(role);
                 for(GroupRoleCapabilityModel grc : getDAO(GroupRoleCapabilityDAO.class).listByGroupRole(gr.getGroupID()).execute())
                     addRoleCapability(role, grc.getCapabilityID());
                 for(EmployeeModel em : getDAO(EmployeeDAO.class).listByGroupRole(getID(), gr.getRoleID()).execute())
                     addRoleEmployee(role, Employee.load(getCache(), em.getId()));
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
    
    @Override
    public String toString() {
        return String.format("[ID:%d Name:%s Roles:%d Parent:%s]", getID(), getName(), _employees.size(), _parent);
    }

    private static class GroupRole {
        private Set<Employee> _employees;
        
        private Set<Integer> _capabilities;
        
        public GroupRole() {
            _employees = new HashSet<>();
            _capabilities = new HashSet<>();
        }
        
        public void addEmployee(Employee employee) {
            _employees.add(employee);
        }
        
        public boolean hasEmployee(Employee employee) {
            return _employees.contains(employee);
        }
        
        public void removeEmployee(Employee employee) {
            _employees.remove(employee);
        }
        
        public ROCollection<Employee> getEmployees() {
            return ROCollection.wrap(_employees);
        }
        
        public void addCapability(Integer capability) {
            _capabilities.add(capability);
        }
        
        public boolean hasCapability(Integer capability) {
            return _capabilities.contains(capability);
        }
        
        public void removeCapability(Integer capability) {
            _capabilities.remove(capability);
        }
        
        public ROCollection<Integer> getCapabilities() {
            return ROCollection.wrap(_capabilities);
        }
    }
}
