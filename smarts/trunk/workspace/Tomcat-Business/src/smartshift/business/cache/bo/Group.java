package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.dao.DAOContext;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.model.GroupEmployeeModel;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.UID;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

public class Group extends CachedObject {
    public static final String TYPE_IDENTIFIER = "G";
    
    private static final SmartLogger logger = new SmartLogger(Group.class);
    
    private String _name;
    private Group _parent;
    private Map<Role, Set<Employee>> _employees;
    
    private GroupModel _model;
    private GroupEmployeeModel _grpEmpModel;

    public Group(Cache cache, String name) {
        super(cache);
        _name = name;
        _employees = new HashMap<Role, Set<Employee>>();
        _employees.put(Role.getBasicRole(cache, this), new HashSet<Employee>());
    }
    
    private Group(Cache cache, GroupModel model) {
        this(cache, model.getName());
        _model = model;
        if(model.getParentID() == null)
        	_parent = null;
        else
        	_parent = Group.load(cache, model.getParentID());
        loadAllChildren();
    }
    
    public String getName() {
        return _name;
    }
    
    public Group getParent() {
    	return _parent;
    }
    
    public boolean hasRole(Role role) {
        return _employees.containsKey(role);
    }

    public void addRole(Role role) {
        if(!hasRole(role))
            _employees.put(role, new HashSet<Employee>());
    }
    
    public void addEmployee(Employee employee, Role role) {
        addRole(role);
        if(!_employees.get(role).contains(employee))
            _employees.get(role).add(employee);
    }
    
    public ROCollection<Role> getRoles() {
    	return ROCollection.wrap(_employees.keySet());
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setName(_name);
                GenericHibernateUtil.update(DAOContext.business(getCache().
                        getBusinessID()).getBusinessSession(), _model);
            } else {
                _model = getDAO(GroupDAO.class).add(_name, null);
            }
        } catch (DBException e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void loadAllChildren() {
        // TODO Load group roles        
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
        if(cache.contains(uid))
            return cache.getGroup(grpID); 
        else {
            GroupModel model = cache.getDAOContext().dao(GroupDAO.class).uniqueByID(grpID);
            Group group = null;
            if(model != null) {
            	group = new Group(cache, model);
                cache.cache(uid, group);
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
        Group grp = new Group(cache, name);
        grp.save();
        cache.cache(new UID(grp), grp);
        return grp;
    }
}
