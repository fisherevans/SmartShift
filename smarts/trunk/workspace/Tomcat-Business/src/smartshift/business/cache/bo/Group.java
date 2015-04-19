package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.dao.GroupEmployeeDAO;
import smartshift.business.hibernate.dao.GroupRoleCapabilityDAO;
import smartshift.business.hibernate.dao.GroupRoleDAO;
import smartshift.business.hibernate.dao.GroupRoleEmployeeDAO;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.hibernate.model.GroupRoleCapabilityModel;
import smartshift.business.hibernate.model.GroupRoleEmployeeModel;
import smartshift.business.hibernate.model.GroupRoleModel;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
import smartshift.common.util.UID;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * an organizational group representing a node in the organizational tree
 * @author drew
 */
public class Group extends CachedObject {
    /** type identifier for a group */
    public static final String TYPE_IDENTIFIER = "G";
    /** capability id for management of a group */
    public static final Integer MANAGER_CAPABILITY = 999;
    
    private static final SmartLogger logger = new SmartLogger(Group.class);
    
    private String _name;
    private Group _parent;
    private Boolean _active = true;
    private final Set<Group> _children;
    private final Map<Role, GroupRole> _employees;

    /**
     * constructor for a newly created group
     * @param cache
     * @param name
     */
    private Group(Cache cache, String name) {
        super(cache);
        _name = name;
        _active = true;
        _children = new HashSet<Group>();
        _employees = new HashMap<Role, GroupRole>();
        addRole(Role.getBasicRole(cache, this));
    }
    
    /**
     * constructor for a newly loaded group
     * @param cache
     * @param id
     */
    private Group(Cache cache, int id) {
        super(cache, id);
        _children = new HashSet<Group>();
        _employees = new HashMap<Role, GroupRole>();
        addRole(Role.getBasicRole(cache, this));
    }
    
    /**
     * set the group name
     * @param name the name to set
     */
    public synchronized void setName(String name) {
        _name = name;
        if(isInitialized())
            getDAO(GroupDAO.class).update(getModel()).enqueue();
    }

    /**
     * @return the group name
     */
    public String getName() {
        return _name;
    }

    /**
     * set the group's parent
     * @param parent the parent to set
     */
    public synchronized void setParent(Group parent) {
        if(_parent != null)
            _parent.childRemoved(this);
        _parent = parent;
        if(_parent != null)
            _parent.childAdded(this);
        if(isInitialized())
            getDAO(GroupDAO.class).update(getModel()).enqueue();
    }
    
    /**
     * @return the group's parent
     */
    public Group getParent() {
    	return _parent;
    }

    /**
     * set the group's active flag
     * @param active the flag to set
     */
    public synchronized void setActive(Boolean active) {
        _active = active;
        if(isInitialized())
            getDAO(GroupDAO.class).update(getModel()).enqueue();
    }
    
    /**
     * @return the group's active flag
     */
    public Boolean getActive() {
		return _active;
	}
    
	// --- Child Groups
	
    /**
     * a child group was added
     * @param grp the child
     * @return true if the child was added successfully
     */
    protected boolean childAdded(Group grp) {
        return _children.add(grp);
    }

    /**
     * @return the top-level child groups
     */
    public ROCollection<Group> getChildGroups() {
        return ROCollection.wrap(_children);
    }
    
    /**
     * a child group was removed
     * @param grp the child
     * @return true if the child was removed successfully
     */
    protected boolean childRemoved(Group grp) {
        return _children.remove(grp);
    }
    
    // --- Roles

    /**
     * add a role to the group
     * @param role the role to add
     */
    public void addRole(Role role) {
        if(!hasRole(role)) {
            int id = 0;
            if(!role.isBasicRole()) {
                GroupRoleModel model = getDAO(GroupRoleDAO.class).uniqueByGroupRole(getID(), role.getID()).execute();
                if(model == null) {
                    synchronized(this) {
                        AddTask<GroupRoleModel> task = getDAO(GroupRoleDAO.class).link(getID(), role.getID());
                        task.enqueue();
                        model = task.getModel();
                    }
                }
                id = model.getId();
            }
            synchronized(this) {
                _employees.put(role, new GroupRole(id));
            }
        }
    }
    
    /**
     * @return the roles on the group
     */
    public ROCollection<Role> getRoles() {
        return ROCollection.wrap(_employees.keySet());
    }
    
    /**
     * check if the group has a role
     * @param role the role to check
     * @return true if the group has the role
     */
    public boolean hasRole(Role role) {
        return _employees.containsKey(role);
    }
    
    /**
     * remove a role from the group
     * @param role the role to remove
     */
    public void removeRole(Role role) {
        GroupRole groupRole = _employees.get(role);
        if(groupRole != null) {
            for(Employee employee:groupRole.getEmployees())
                removeRoleEmployee(role, employee);
            synchronized(this) {
                _employees.remove(role);
                getDAO(GroupRoleDAO.class).deleteByID(groupRole.getID());
            }
        }
        
    }
    
    /**
     * add a capability for a role within this group
     * @param role
     * @param capabilityID
     */
    public void addRoleCapability(Role role, Integer capabilityID) {
        if(!hasRole(role))
            throw new RuntimeException(String.format("Group:%d does not have the Role:%d to set capability for.", getID(), role.getID()));
        synchronized(this) {
            GroupRole groupRole = _employees.get(role);
            groupRole.addCapability(capabilityID);
            role.capabilityAdded(this, capabilityID);
            int count = getDAO(GroupRoleCapabilityDAO.class).linkCount(groupRole.getID(), capabilityID).execute();
            if(count == 0)
                getDAO(GroupRoleCapabilityDAO.class).link(groupRole.getID(), capabilityID).enqueue();
        }
    }
    
    /**
     * get the capabilities for a role within this group
     * @param role
     * @return the capabilities
     */
    public ROCollection<Integer> getRoleCapabilities(Role role) {
        return _employees.get(role).getCapabilities();
    }
    
    /**
     * does a role have the given capability within this group
     * @param role
     * @param capabilityID
     * @return true if the role has the capability
     */
    public boolean hasRoleCapability(Role role, Integer capabilityID) {
        if(!hasRole(role))
            throw new RuntimeException(String.format("Group:%d does not have the Role:%d to get capability from.", getID(), role.getID()));
        return _employees.get(role).hasCapability(capabilityID);
    }
    
    // --- Role Employees
    
    /**
     * add an employee to a role within this group
     * @param role
     * @param employee
     */
    public void addRoleEmployee(Role role, Employee employee) {
        if(!hasRole(role))
            throw new RuntimeException(String.format("Group:%d does not have the Role:%d to add employee too.", getID(), role.getID()));
        synchronized(this) {
            GroupRole groupRole = _employees.get(role);
            groupRole.addEmployee(employee);
            employee.groupRoleAdded(this, role);
            if(getDAO(GroupRoleEmployeeDAO.class).linkCount(groupRole.getID(), employee.getID()).execute() == 0)
                getDAO(GroupRoleEmployeeDAO.class).link(groupRole.getID(), employee.getID());
        }
    }
    
    /**
     * get all employees on a role within this group
     * @param role
     * @return the employees
     */
    public ROCollection<Employee> getRoleEmployees(Role role) {
        return _employees.get(role).getEmployees();
    }
    
    /**
     * remove an employee from a role within this group
     * @param role
     * @param employee
     */
    public void removeRoleEmployee(Role role, Employee employee) {
        GroupRole groupRole = _employees.get(role);
        if(groupRole != null) {
            synchronized(this) {
                groupRole.removeEmployee(employee);
                employee.groupRoleRemoved(this, role);
                getDAO(GroupRoleEmployeeDAO.class).unlink(groupRole.getID(), employee.getID()).enqueue();
            }
        }
    }
    
    // --- Employees
    
    /**
     * remove an employee from this group
     * @param employee
     */
    public void removeEmployee(Employee employee) {
        if(equals(employee.getHomeGroup()))
            throw new RuntimeException(String.format("Employee:%d has a parent group of %d, so cannot remove from %d",
                    employee.getID(), employee.getHomeGroup().getID(), this.getID()));
        for(Role role:_employees.keySet())
            removeRoleEmployee(role, employee);
        synchronized(this) {
            employee.groupRemoved(this);
            getDAO(GroupEmployeeDAO.class).unlink(getID(), employee.getID()).enqueue();
        }
    }
    
    // --- Misc
    
    /**
     * delete this group
     * NOTE: root groups cannot be deleted
     */
    public void delete() {
        if(_parent == null)
            throw new RuntimeException(String.format("Group:%d is the root group, and can't be deleted", getID()));
        // deactivate the group
        setActive(false);
        // migrate the child groups up the hierarchy
        for(Group g : getChildGroups()) {
            g.setParent(_parent);
        }
        // migrate the employees with this as their homegroup up the hierarchy
        for(Role r : getRoles()) {
            for(Employee e : getRoleEmployees(r)) {
                if(e.getHomeGroup().equals(this))
                    e.setHomeGroup(_parent);
                e.groupRoleRemoved(this, r);
            }
        }
        getCache().decache(getUID());
    }

    /**
     * return true if this group is a valid parent of another
     * @param group the group to check
     * @return true if this is a valid parent of group
     */
    public boolean isValidParentOf(Group group) {
        Group parent = getParent();
        while(parent != null) {
            if(parent == group)
                return false;
            parent = parent.getParent();
        }
        return true;
    }
    
    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    @Override
    public GroupModel getModel() {
        GroupModel model = new GroupModel();
        model.setId(getID());
        model.setName(_name);
        model.setActive(_active);
        model.setParentID(_parent == null ? null : _parent.getID());
        return model;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
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

    /**
     * @see smartshift.common.util.Identifiable#typeCode()
     */
    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    /**
     * load a group into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param grpID the id of the group to load
     * @return the group requested
     */
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
            group.initialize(null);
            return group;
        }
    }
    
    /**
     * @see smartshift.business.cache.bo.CachedObject#init()
     */
    @Override
    public void init() {
        GroupModel model = getCache().getDAOContext().dao(GroupDAO.class).uniqueByID(getID()).execute();
        _name = model.getName();
        _active = model.getActive();
        if(model.getParentID() == null)
            _parent = null;
        else
            _parent = Group.load(getCache(), model.getParentID());
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
        GroupDAO dao = grp.getDAO(GroupDAO.class);
        grp.setID(dao.getNextID());
        dao.add(grp.getModel()).enqueue();
        cache.cache(new UID(grp), grp);
        return grp;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("[ID:%d Name:%s Roles:%d Parent:%s]", getID(), getName(), _employees.size(), _parent);
    }

    /** a mapping from a group to a role, which has a set of employees and capabilities */
    private static class GroupRole {
        private final int _id;
        private Set<Employee> _employees;      
        private Set<Integer> _capabilities;
        
        /**
         * constructor for a new group role mapping
         * @param id
         */
        public GroupRole(int id) {
            _id = id;
            _employees = new HashSet<>();
            _capabilities = new HashSet<>();
        }
        
        /**
         * @return the group role id
         */
        public Integer getID() {
            return _id;
        }
        
        /**
         * add an employee to the group role
         * @param employee
         */
        public void addEmployee(Employee employee) {
            _employees.add(employee);
        }
        
        /**
         * does this group role have the given employee?
         * @param employee
         * @return true if the group role has the employee
         */
        public boolean hasEmployee(Employee employee) {
            return _employees.contains(employee);
        }
        
        /**
         * remove an employee from the group role
         * @param employee
         */
        public void removeEmployee(Employee employee) {
            _employees.remove(employee);
        }
        
        /**
         * @return the employees on the group role
         */
        public ROCollection<Employee> getEmployees() {
            return ROCollection.wrap(_employees);
        }
        
        /**
         * add a capability to the group role
         * @param capability
         */
        public void addCapability(Integer capability) {
            _capabilities.add(capability);
        }
        
        /**
         * does the group role have the given capability?
         * @param capability the id of the capability to look for
         * @return true if the group role has the capability
         */
        public boolean hasCapability(Integer capability) {
            return _capabilities.contains(capability);
        }
        
        /**
         * remove a capability from the group role
         * @param capability
         */
        public void removeCapability(Integer capability) {
            _capabilities.remove(capability);
        }
        
        /**
         * @return the capabilities of the group role
         */
        public ROCollection<Integer> getCapabilities() {
            return ROCollection.wrap(_capabilities);
        }
    }
}
