package smartshift.business.cache.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.util.UID;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

public class Employee extends CachedObject {
    public static final String TYPE_IDENTIFIER = "E";
    
    private static final SmartLogger logger = new SmartLogger(Employee.class);
    
    private String _firstName;
    private String _lastName;
    private Group _homeGroup;
    private Map<Group, Set<Role>> _roles;
    private List<Availability> _availabilities;
    
    private EmployeeModel _model;
    
    public Employee(Cache cache, String first, String last, Group home) {
        super(cache);
        _firstName = first;
        _lastName = last;
        _homeGroup = home;
        _roles = new HashMap<Group, Set<Role>>();
        _availabilities = new ArrayList<Availability>();
    }
    
    private Employee(Cache cache, EmployeeModel model) {
        this(cache, model.getFirstName(), model.getLastName(), Group.load(cache, model.getDefaultGroupID()));
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
    
    public void addRole(Role role, Group parent) {
        if(!_roles.containsKey(parent))
            _roles.put(parent, new HashSet<Role>());
        _roles.get(parent).add(role);
    }
    
    public ROCollection<Role> getRoles(Group group) {
        return ROCollection.wrap(_roles.get(group));
    }
    
    public ROCollection<Group> getGroups() {
    	return ROCollection.wrap(_roles.keySet());
    }
    
    public boolean manages(Employee other) {
    	// TODO Drew, need to find out if this employee manages the other, or is the same
    	return false;
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
                getDAO(EmployeeDAO.class).update(_model);
            } else {
                _homeGroup.save();
                _model = getDAO(EmployeeDAO.class).add(_firstName, _lastName, _homeGroup.getID()).execute();
            }
        } catch (HibernateException e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void loadAllChildren() {
        try {
            for(GroupModel gm : getDAO(GroupDAO.class).listByEmployee(getID()).execute()) {
                _roles.put(Group.load(getCache(), gm.getId()), new HashSet<Role>()); 
            }
        } catch(Exception e) {
            logger.error("Failed to load children", e);
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
                employee = new Employee(cache, model);
                cache.cache(uid, employee);
            }
            return employee;
        }
    }
    
    public static Employee create(int businessID, String first, String last, int homeGroupID) {
        Cache cache = Cache.getCache(businessID);
        Employee emp = new Employee(cache, first, last, Group.load(cache, homeGroupID));
        emp.save();
        cache.cache(new UID(emp), emp);
        return emp;
    }
}
