package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.dao.RoleDAO;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.hibernate.model.RoleModel;
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
    
    private EmployeeModel _model;
    
    private Employee(Cache cache, String first, String last, Group home) {
        super(cache);
        _firstName = first;
        _lastName = last;
        _homeGroup = home;
        _active = true;
        _homeGroup.addRoleEmployee(Role.getBasicRole(cache, _homeGroup), this);
        _roles = new HashMap<Group, Set<Role>>();
    }
    
    private Employee(Cache cache, int id) {
        super(cache, id);
        _roles = new HashMap<Group, Set<Role>>();
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }
    
    public String getFirstName() {
        return _firstName;
    }
    
    public void setLastName(String lastName) {
        _lastName = lastName;
    }
    
    public String getLastName() {
        return _lastName;
    }
    
    public String getDisplayName() {
        return _firstName + " " + _lastName;
    }
    
    public void setHomeGroup(Group homeGroup) {
        _homeGroup = homeGroup;
    }
    
    public Group getHomeGroup() {
        return _homeGroup;
    }

    public void setActive(Boolean active) {
        _active = active;
    }

    public Boolean getActive() {
        return _active;
    }
    
    // --- GROUPS
    
    protected void groupAdded(Group group) {
        if(!_roles.containsKey(group))
            _roles.put(group, new HashSet<Role>());
    }
    
    public ROCollection<Group> getGroups() {
        return ROCollection.wrap(_roles.keySet());
    }
    
    protected void groupRemoved(Group group) {
        _roles.remove(group);
    }
    
    protected void groupRoleAdded(Group group, Role role) {
        groupAdded(group);
        _roles.get(group).add(role);
    }
    
    // --- GROUP ROLES
    
    public ROCollection<Role> getRoles(Group group) {
        return ROCollection.wrap(_roles.get(group));
    }
    
    protected void groupRoleRemoved(Group group, Role role) {
        Set<Role> groupRoles = _roles.get(group);
        if(groupRoles != null) {
            groupRoles.remove(role);
            if(groupRoles.size() == 0)
                groupRemoved(group);
        }
    }
    
    // --- MISC
    
    public boolean manages(Employee other) {
        // TODO Drew, need to find out if this employee manages the other, or is the same
        logger.error("Hit a non-implemeneted block! deleteEmployee()");
        throw new RuntimeException("To implement!");
    }

    public boolean manages(Group group) {
        // TODO Drew, need to find out if this employee manages this group
        logger.error("Hit a non-implemeneted block! deleteEmployee()");
        throw new RuntimeException("To implement!");
    }
    
    public boolean belongsTo(Group group) {
        // TODO Drew, true if this employee belongs in the given group - check this work?
        for(Group employeeGroup:getGroups())
            if(employeeGroup.getID() == group.getID())
                return true;
        return false;
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setFirstName(_firstName);
                _model.setLastName(_lastName);
                _model.setDefaultGroupID(_homeGroup.getID());
                _model.setActive(_active);
                getDAO(EmployeeDAO.class).update(_model);
            } else {
                _homeGroup.save();
                Integer id;
                try {
                    AccountsServiceInterface accts = RMIClient.getAccountsService();
                    id = accts.getNextID(AppConstants.NEXT_ID_NAME_EMPLOYEE);
                    if(id == null)
                        throw new Exception("An error occurred on the accounts side! ID was null");
                    logger.info("Adding employee, got next ID from accounts: " + id);
                } catch(Exception e) {
                    logger.error("Failed to get next employee id from accounts servive!");
                    throw new Exception("Not currently connected to the Accounts Service!", e);
                }
                _model = getDAO(EmployeeDAO.class).add(id, _firstName, _lastName, _homeGroup.getID()).execute();
                setID(id);
            }
        } catch (Exception e) {
            logger.warn("Failed to save the employee!", e);
        }
    }

    @Override
    public void loadAllChildren() {
        try {
            for(GroupModel gm : getDAO(GroupDAO.class).listByEmployee(getID()).execute()) {
                Group group = Group.load(getCache(), gm.getId());
                _roles.put(group, new HashSet<Role>());
                for(RoleModel roleModel : getDAO(RoleDAO.class).listByGroupEmployee(gm.getId(), getID()).execute()) {
                    Role role = Role.load(getCache(), roleModel.getId());
                    _roles.get(group).add(role);
                }
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
        _model = model;
    }
    
    public static Employee create(int businessID, String first, String last, int homeGroupID) {
        Cache cache = Cache.getCache(businessID);
        Employee emp = new Employee(cache, first, last, Group.load(cache, homeGroupID));
        emp.save();
        cache.cache(new UID(emp), emp);
        return emp;
    }
    
    public void delete() {
        // TODO Drew this needs to set the flag, save to the DB and remove any relations.
        logger.error("Hit a non-implemeneted block! delete()");
        throw new RuntimeException("To implement!");
    }
}
