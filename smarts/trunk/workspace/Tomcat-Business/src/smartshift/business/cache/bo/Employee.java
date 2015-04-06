package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.dao.AvailabilityInstanceDAO;
import smartshift.business.hibernate.dao.AvailabilityTemplateDAO;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.dao.GroupEmployeeDAO;
import smartshift.business.hibernate.model.AvailabilityInstanceModel;
import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.rmi.RMIClient;
import smartshift.common.rmi.interfaces.AccountsServiceInterface;
import smartshift.common.util.UID;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

public class Employee extends CachedObject {
    public static final String TYPE_IDENTIFIER = "E";
    
    private static final SmartLogger logger = new SmartLogger(Employee.class);
    
    private String _firstName;
    private String _lastName;
    private Group _homeGroup;
    private Boolean _active;
    private final Map<Group, Set<Role>> _roles;
    private List<AvailabilityInstance> _availabilities;
    private List<AvailabilityTemplate> _availabilityTemplates;
    
    private Employee(Cache cache, String first, String last, Group home) {
        super(cache);
        _firstName = first;
        _lastName = last;
        _homeGroup = home;
        _active = true;
        _roles = new HashMap<Group, Set<Role>>();
        
        _homeGroup.addRoleEmployee(Role.getBasicRole(cache, _homeGroup), this);
    }
    
    private Employee(Cache cache, int id) {
        super(cache, id);
        _roles = new HashMap<Group, Set<Role>>();
    }

    public synchronized void setFirstName(String firstName) {
        _firstName = firstName;
        getDAO(EmployeeDAO.class).update(getModel()).enqueue();
    }
    
    public String getFirstName() {
        return _firstName;
    }
    
    public synchronized void setLastName(String lastName) {
        _lastName = lastName;
        getDAO(EmployeeDAO.class).update(getModel()).enqueue();
    }
    
    public String getLastName() {
        return _lastName;
    }
    
    public String getDisplayName() {
        return _firstName + " " + _lastName;
    }
    
    public void setHomeGroup(Group homeGroup) {
        if(!belongsTo(homeGroup, false))
            throw new RuntimeException(String.format("Cannot set homegroup:%d on Employee:%d because the employee is not already a member.", homeGroup.getID(), getID()));
        synchronized(this) {
            _homeGroup = homeGroup;
            getDAO(EmployeeDAO.class).update(getModel()).enqueue();
        }
    }
    
    public Group getHomeGroup() {
        return _homeGroup;
    }

    public synchronized void setActive(Boolean active) {
        _active = active;
        getDAO(EmployeeDAO.class).update(getModel()).enqueue();
    }

    public Boolean getActive() {
        return _active;
    }
    
    // --- GROUPS
    
    protected synchronized void groupAdded(Group group) {
        if(!_roles.containsKey(group))
            _roles.put(group, new HashSet<Role>());
        if(getDAO(GroupEmployeeDAO.class).linkCount(group.getID(), getID()).execute() < 1)
            getDAO(GroupEmployeeDAO.class).link(group.getID(), getID()).enqueue();
    }
    
    public ROCollection<Group> getGroups() {
        return ROCollection.wrap(_roles.keySet());
    }
    
    protected synchronized void groupRemoved(Group group) {
        _roles.remove(group);
        if(getDAO(GroupEmployeeDAO.class).linkCount(group.getID(), getID()).execute() == 1)
            getDAO(GroupEmployeeDAO.class).unlink(group.getID(), getID()).enqueue();
    }
    
    // --- GROUP ROLES
    
    protected synchronized void groupRoleAdded(Group group, Role role) {
        groupAdded(group);
        _roles.get(group).add(role);
    }
    
    public ROCollection<Role> getRoles(Group group) {
        return ROCollection.wrap(_roles.get(group));
    }
    
    protected void groupRoleRemoved(Group group, Role role) {
        Set<Role> groupRoles = _roles.get(group);
        if(groupRoles != null) {
            synchronized(this) {
                groupRoles.remove(role);
                if(groupRoles.isEmpty())
                    groupRemoved(group);
            }
        }
    }
    
    // --- AVAILABILITY
    
    private void initAvailabilities() {
        try {
            for(AvailabilityInstanceModel aim : getDAO(AvailabilityInstanceDAO.class).listByEmployee(getID()).execute()) {
                _availabilities.add(AvailabilityInstance.load(getCache(), aim.getId()));
            }
        } catch(Exception e) {
            logger.error("Failed to load availabilities", e);
            throw e;
        }
    }
    
    private void initAvailabilityTemplates() {
        try {
            for(AvailabilityTemplateModel atm : getDAO(AvailabilityTemplateDAO.class).listByEmployee(getID()).execute()) {
                _availabilityTemplates.add(AvailabilityTemplate.load(getCache(), atm.getId()));
            }
        } catch(Exception e) {
            logger.error("Failed to load availability templates", e);
            throw e;
        }
    }
    
    // --- MISC
    
    public boolean manages(Employee other) {
        // TODO only check for master manager
        if(other.getGroups().size() == 0)
            return true;
        for(Group group:_roles.keySet()) {
            if(manages(group, true) && other.belongsTo(group, true))
                return true;
        }
        return false;
    }

    public boolean manages(Group group, boolean checkParent) {
        // TODO only check for master manager
        if(_roles.get(group) != null)
            for(Role role:_roles.get(group))
                if(group.hasRoleCapability(role, Group.MANAGER_CAPABILITY))
                    return true;
        return checkParent && group.getParent() != null && 
                manages(group.getParent(), checkParent);
    }
    
    public boolean belongsTo(Group group, boolean checkParent) {
        for(Group employeeGroup:getGroups()) {
            if(employeeGroup.getID() == group.getID())
                return true;
            if(checkParent && employeeGroup.getParent() != null && 
                    belongsTo(employeeGroup, checkParent))
                return true;
        }
        return false;
    }
    
    public void delete() {
        setActive(false);
        for(Group group:getGroups()) {
            group.removeEmployee(this);
        }
        getCache().decache(getUID());
    }
    
    public EmployeeModel getModel() {
        EmployeeModel model = new EmployeeModel();
        model.setId(getID());
        model.setFirstName(_firstName);
        model.setLastName(_lastName);
        model.setDefaultGroupID(_homeGroup.getID());
        model.setActive(_active);
        return model;
    }

    @Override
    public void loadAllChildren() {
        try {
            for(GroupModel gm : getDAO(GroupDAO.class).listByEmployee(getID()).execute()) {
                // Group.loadChildren loads the employee's roles.
                Group.load(getCache(), gm.getId());
            }
        } catch(Exception e) {
            logger.error("Failed to load children", e);
            throw e;
        }
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    public static Employee load(Cache cache, int empID) {
    	UID uid = new UID(TYPE_IDENTIFIER, empID);
        if(cache.contains(uid)) {
            logger.debug("Cache contains employee id: " + empID);
            return cache.getEmployee(empID); 
        } else {
            logger.debug("Cache does not contain employee id: " + empID);
            Employee employee = new Employee(cache, empID);
            cache.cache(uid, employee);
            employee.loadAllChildren();
            employee.init();
            return employee;
        }
    }
    
    public void init() {
        EmployeeModel model = getCache().getDAOContext().dao(EmployeeDAO.class).uniqueByID(getID()).execute();
        _firstName = model.getFirstName();
        _lastName = model.getLastName();
        _homeGroup = Group.load(getCache(), model.getDefaultGroupID());
    }
    
    public static Employee create(int businessID, String first, String last, int homeGroupID) {
        Cache cache = Cache.getCache(businessID);
        Employee emp = new Employee(cache, first, last, Group.load(cache, homeGroupID));
        Integer id;
        try {
            AccountsServiceInterface accts = RMIClient.getAccountsService();
            id = accts.getNextGlobalID(AppConstants.NEXT_ID_NAME_EMPLOYEE);
            if(id == null)
                throw new Exception("An error occurred on the accounts side! ID was null");
            logger.info("Adding employee, got next ID from accounts: " + id);
        } catch(Exception e) {
            logger.error("Failed to get next employee id from accounts service!");
            throw new RuntimeException("Not currently connected to the Accounts Service!", e);
        }
        emp.setID(id);
        emp.getDAO(EmployeeDAO.class).add(emp.getModel()).enqueue();
        cache.cache(new UID(emp), emp);
        return emp;
    }
    
    @Override
    public String toString() {
        return String.format("[ID:%d Name:%s Groups:%d Home:%s]", getID(), getDisplayName(), _roles.size(), _homeGroup);
    }
}
