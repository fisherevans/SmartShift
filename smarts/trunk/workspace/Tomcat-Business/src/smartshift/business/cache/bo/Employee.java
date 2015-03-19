package smartshift.business.cache.bo;

import java.util.ArrayList;
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
    private final List<Availability> _availabilities;
    
    private EmployeeModel _model;
    
    public Employee(Cache cache, String first, String last, Group home, Boolean active) {
        super(cache);
        _firstName = first;
        _lastName = last;
        _homeGroup = home;
        _active = active;
        _homeGroup.addEmployee(this);
        _roles = new HashMap<Group, Set<Role>>();
        _availabilities = new ArrayList<Availability>();
    }
    
    private Employee(Cache cache, EmployeeModel model) {
        this(cache, model.getFirstName(), model.getLastName(), Group.load(cache, model.getDefaultGroupID()), model.getActive());
        _model = model;
        loadAllChildren();
    }
    
    public String getDisplayName() {
        return _firstName + " " + _lastName;
    }
    
    public String getFirstName() {
        return _firstName;
    }
    
    public String getLastName() {
        return _lastName;
    }
    
    public Group getHomeGroup() {
        return _homeGroup;
    }
    
    public void setFirstName(String firstName) {
        _firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        _lastName = lastName;
    }
    
    public void setHomeGroup(Group homeGroup) {
        _homeGroup = homeGroup;
    }
    
    public void addGroup(Group group) {
        if(!_roles.containsKey(group))
            _roles.put(group, new HashSet<Role>());
    }
    
    public void addGroupRole(Role role, Group group) {
        addGroup(group);
        _roles.get(group).add(role);
    }
    
    public ROCollection<Role> getRoles(Group group) {
        return ROCollection.wrap(_roles.get(group));
    }
    
    public ROCollection<Group> getGroups() {
    	return ROCollection.wrap(_roles.keySet());
    }
    
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
        for(Group employeeGroups:getGroups())
            if(employeeGroups.getID() == group.getID())
                return true;
        return false;
    }

    public Boolean getActive() {
		return _active;
	}

	public void setActive(Boolean active) {
		_active = active;
	}

	public static Employee getEmployee(Cache cache, EmployeeModel model) {     
        Employee employee = cache.getEmployee(model.getId());
        if(employee == null)
            employee = new Employee(cache, model);
        return employee;
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

    @Override
    public int getID() {
        if(_model == null)
            return -1;
        return _model.getId();
    }
    
    public static Employee load(Cache cache, int empID) {
    	UID uid = new UID(TYPE_IDENTIFIER, empID);
        if(cache.contains(uid))
            return cache.getEmployee(empID); 
        else {
            EmployeeModel model = cache.getDAOContext().dao(EmployeeDAO.class).uniqueByID(empID).execute();
            Employee employee = null;
            if(model != null) {
                cache.cache(uid, null);
                employee = new Employee(cache, model);
                cache.cache(uid, employee);
            }
            return employee;
        }
    }
    
    public static Employee create(int businessID, String first, String last, int homeGroupID) {
        Cache cache = Cache.getCache(businessID);
        Employee emp = new Employee(cache, first, last, Group.load(cache, homeGroupID), true);
        emp.save();
        cache.cache(new UID(emp), emp);
        return emp;
    }
}
