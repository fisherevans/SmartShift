package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

public class Employee extends CachedObject implements Stored{
    private static final SmartLogger logger = new SmartLogger(Employee.class);
    private String _firstName;
    private String _lastName;
    private Group _homeGroup;
    private Map<Group, Set<Role>> _roles;
    
    private EmployeeModel _model;
    
    public Employee(Cache cache, String first, String last, Group home) {
        super(cache);
        _firstName = first;
        _lastName = last;
        _homeGroup = home;
        _roles = new HashMap<Group, Set<Role>>();
        _roles.put(_homeGroup, new HashSet<Role>());
        _roles.get(_homeGroup).add(Role.getBasicRole(cache, _homeGroup));
    }
    
    private Employee(Cache cache, EmployeeModel model) {
        this(cache, model.getFirstName(), model.getLastName(), Group.getGroup(cache, model.getDefaultGroup()));
        _model = model;
    }
    
    public String getDisplayName() {
        return _firstName + _lastName;
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

    public static Employee getEmployee(Cache cache, EmployeeModel model) {     
        Employee employee = cache.getEmployee(model.getId());
        if(employee == null)
            employee = new Employee(cache, model);
        return employee;
    }

    @Override
    public void save() {
        try {
            if(_model != null)
                GenericHibernateUtil.save(EmployeeDAO.getBusinessSession(), _model);
            else {
                _homeGroup.save();
                _model = EmployeeDAO.addEmployee(_firstName, _lastName, _homeGroup.getModel());
            }
        } catch (DBException e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }
}
