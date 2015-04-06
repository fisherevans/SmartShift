package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.AvailabilityTemplateDAO;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.dao.GroupEmployeeDAO;
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
    
    public synchronized void setName(String name) {
        _name = name;
        getDAO(GroupDAO.class).update(getModel()).enqueue();
    }

    public String getName() {
        return _name;
    }

    public synchronized void setParent(Group parent) {
        if(_parent != null)
            _parent.childRemoved(this);
        _parent = parent;
        if(_parent != null)
            _parent.childAdded(this);
        getDAO(GroupDAO.class).update(getModel()).enqueue();
    }
    
    public Group getParent() {
    	return _parent;
    }

    public synchronized void setActive(Boolean active) {
        _active = active;
        getDAO(GroupDAO.class).update(getModel()).enqueue();
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

    public synchronized void addRole(Role role) {
        if(!hasRole(role)) {
            Integer grID = getDAO(GroupRoleDAO.class).link(getID(), role.getID()).execute().getId();
            _employees.put(role, new GroupRole(grID));
        }
    }
    
    public ROCollection<Role> getRoles() {
        return ROCollection.wrap(_employees.keySet());
    }
    
    public boolean hasRole(Role role) {
        return _employees.containsKey(role);
    }
    
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
    
    // --- Role Capabilities
    
    public void addRoleCapability(Role role, Integer capabilityID) {
        if(!hasRole(role))
            throw new RuntimeException(String.format("Group:%d does not have the Role:%d to set capability for.", getID(), role.getID()));
        synchronized(this) {
            GroupRole groupRole = _employees.get(role);
            groupRole.addCapability(capabilityID);
            role.capabilityAdded(this, capabilityID);
            getDAO(GroupRoleCapabilityDAO.class).link(groupRole.getID(), capabilityID).enqueue();
        }
    }
    
    public ROCollection<Integer> getRoleCapabilities(Role role) {
        return _employees.get(role).getCapabilities();
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
        synchronized(this) {
            GroupRole groupRole = _employees.get(role);
            groupRole.addEmployee(employee);
            employee.groupRoleAdded(this, role);
            getDAO(GroupRoleEmployeeDAO.class).link(groupRole.getID(), employee.getID());
        }
    }
    
    public ROCollection<Employee> getRoleEmployees(Role role) {
        return _employees.get(role).getEmployees();
    }
    
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
    
    public void delete() {
        if(_parent == null)
            throw new RuntimeException(String.format("Group:%d is the root group, and can't be deleted", getID()));
        setActive(false);
        for(Role r : getRoles()) {
            for(Employee e : getRoleEmployees(r)) {
                if(e.getHomeGroup().equals(this))
                    e.setHomeGroup(_parent);
                e.groupRoleRemoved(this, r);
            }
        }
        getCache().decache(getUID());
    }

    public boolean isValidParentOf(Group group) {
        Group parent = getParent();
        while(parent != null) {
            if(parent == group)
                return false;
            parent = parent.getParent();
        }
        return true;
    }
    
    public GroupModel getModel() {
        GroupModel model = new GroupModel();
        model.setId(getID());
        model.setName(_name);
        model.setActive(_active);
        model.setParentID(_parent == null ? null : _parent.getID());
        return model;
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
    
    @Override
    public String toString() {
        return String.format("[ID:%d Name:%s Roles:%d Parent:%s]", getID(), getName(), _employees.size(), _parent);
    }

    private static class GroupRole {
        private final Integer _id;
        
        private Set<Employee> _employees;
        
        private Set<Integer> _capabilities;
        
        public GroupRole(Integer id) {
            _id = id;
            _employees = new HashSet<>();
            _capabilities = new HashSet<>();
        }
        
        public Integer getID() {
            return _id;
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
