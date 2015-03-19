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

    public Group(Cache cache, String name, Boolean active) {
        super(cache);
        _name = name;
        _active = active;
        _children = new HashSet<Group>();
        _employees = new HashMap<Role, Set<Employee>>();
        _employees.put(Role.getBasicRole(cache, this), new HashSet<Employee>());
    }
    
    private Group(Cache cache, GroupModel model) {
        this(cache, model.getName(), model.getActive());
        _model = model;
        if(model.getParentID() == null)
        	_parent = null;
        else
        	_parent = Group.load(cache, model.getParentID());
        loadAllChildren();
    }
    
    public void setName(String name) {
        _name = name;
    }

    public void setParent(Group parent) {
        _parent = parent;
    }

    public String getName() {
        return _name;
    }
    
    public Group getParent() {
    	return _parent;
    }
    
    public Boolean getActive() {
		return _active;
	}

	public void setActive(Boolean active) {
		_active = active;
	}

	public ROCollection<Group> getChildGroups() {
        return ROCollection.wrap(_children);
    }
    
    public boolean addChild(Group grp) {
        return _children.add(grp);
    }
    
    public boolean hasRole(Role role) {
        return _employees.containsKey(role);
    }

    public void addRole(Role role) {
        if(!hasRole(role))
            _employees.put(role, new HashSet<Employee>());
    }
    
    public ROCollection<Employee> getRoleEmployees(Role role) {
        Set<Employee> roleEmployees = _employees.get(role);
        return ROCollection.wrap(roleEmployees == null ? new ArrayList() : roleEmployees);
    }
    
    public void addEmployeeRole(Employee employee, Role role) {
        addRole(role);
        if(!_employees.get(role).contains(employee))
            _employees.get(role).add(employee);
    }
    
    public void addEmployee(Employee employee) {
        addEmployeeRole(employee, Role.getBasicRole(getCache(), this));
    }
    
    public ROCollection<Role> getRoles() {
    	return ROCollection.wrap(_employees.keySet());
    }

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

    @Override
    public int getID() {
        if(_model == null)
            return -1;
        return _model.getId();
    }
    
    public static Group load(Cache cache, int grpID) {
    	UID uid = new UID(TYPE_IDENTIFIER, grpID);
        if(cache.contains(uid)) {
            logger.debug("Cache has group: " + grpID);
            return cache.getGroup(grpID); 
        } else {
            logger.debug("Cache does not have group: " + grpID);
            GroupModel model = cache.getDAOContext().dao(GroupDAO.class).uniqueByID(grpID).execute();
            logger.debug("Got model: " + model);
            Group group = null;
            if(model != null) {
                cache.cache(uid, new PlaceHolderObject(cache, TYPE_IDENTIFIER, grpID));
            	group = new Group(cache, model);
                cache.cache(uid, group);
                logger.debug("cached group: " + group);
            }
            return group;
        }
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
        Group grp = new Group(cache, name, true);
        grp.save();
        cache.cache(new UID(grp), grp);
        return grp;
    }

    public boolean isValidParentOf(Group group) {
        logger.error("Hit a non-implemeneted block! isValidParentOf()");
        throw new RuntimeException("To implement!");
        // TODO drew - true if the passed group is a valid child of this group. Looking to avoid prevent infinite loops and what so
    }
}
