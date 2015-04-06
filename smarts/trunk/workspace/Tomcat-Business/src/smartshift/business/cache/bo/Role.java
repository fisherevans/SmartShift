package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.dao.RoleDAO;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.util.UID;
import smartshift.common.util.log4j.SmartLogger;

public class Role extends CachedObject {
    public static final String TYPE_IDENTIFIER = "R";
    
    private static final SmartLogger logger = new SmartLogger(Role.class);
    
    private String _name;
    private Map<Group, Set<Integer>> _capabilities;
    
    private static String BASIC_ROLE_NAME = "basic";
    private static int BASIC_ROLE_ID = 0;
    
    private static final class BasicRole extends Role {
        public BasicRole(Cache cache, Group parent) {
            super(cache, BASIC_ROLE_NAME, parent);
        }
        
        @Override
        public int getID() {
            return BASIC_ROLE_ID;
        }
    }
    
    private Role(Cache cache, String name) {
        super(cache);
        _name = name;
        _capabilities = new HashMap<Group, Set<Integer>>();
    }
    
    private Role(Cache cache, String name, Group parent) {
        this(cache, name);
        _capabilities.put(parent, new HashSet<Integer>());
        parent.addRole(this);
    }
    
    private Role(Cache cache, RoleModel model) {
        this(cache, model.getName());
    }
    
    private Role(Cache cache, int id) {
        super(cache, id);
        _capabilities = new HashMap<Group, Set<Integer>>();
    }

    protected synchronized void setName(String name) {
        _name = name;
    }
    
    public String getName() {
        return _name;
    }
    
    public static Role getBasicRole(Cache cache, Group parent) {
        Role basicRole = new BasicRole(cache, parent);
        parent.addRole(basicRole);
        return basicRole;
    }
    
    public void capabilityAdded(Group group, Integer capabilityID) {
        _capabilities.get(group).add(capabilityID);
    }
    
    public Role renameForGroup(Group group, String newName) {
        if(_capabilities.containsKey(group) && _capabilities.keySet().size() == 1) {
            synchronized(this) {
                setName(newName);
            }
            return this;
        }            
        Role forGrp = fork();
        forGrp.setName(newName);
        group.addRole(forGrp);
        for(Employee e : group.getRoleEmployees(this))
            group.addRoleEmployee(forGrp, e);
        for(Integer i : group.getRoleCapabilities(this))
            group.addRoleCapability(forGrp, i);
        group.removeRole(this);
        return forGrp;
    }
    
    private Role fork() {
        Role newRole = create(getCache().getBusinessID(), getName());
        getCache().cache(new UID(newRole), newRole);
        return newRole;
    }
    
    public RoleModel getModel() {
        RoleModel model = new RoleModel();
        model.setId(getID());
        model.setName(_name);
        return model;
    }

    @Override
    public void loadAllChildren() {
        try {
            for(GroupModel gm : getDAO(GroupDAO.class).listByRole(getID()).execute()){
               int grpID = gm.getId();
                Group grp = Group.load(getCache(), grpID);
                _capabilities.put(grp, new HashSet<Integer>());
            }
       } catch(Exception e) {
           logger.error("Failed to load children", e);
       }  
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    public static Role load(Cache cache, int roleID) {
        UID uid = new UID(TYPE_IDENTIFIER, roleID);
        if(cache.contains(uid))
            return cache.getRole(roleID); 
        else {           
            Role role = new Role(cache, roleID);
            cache.cache(uid, role);
            role.loadAllChildren();
            role.init();
            return role;
        }
    }
    
    // never call in the initial load. plz plz plz.
    public static Role loadByName(Cache cache, String roleName) {
        Role role = cache.getRole(roleName);
        if(role == null) {
            RoleModel model = cache.getDAOContext().dao(RoleDAO.class).uniqueByCriteria(Restrictions.eq("name", roleName)).execute();
            if(model != null) {
                UID uid = new UID(TYPE_IDENTIFIER, model.getId());
                role = new Role(cache, model);
                cache.cache(uid, role);
            }
        }
        return role;
    }
    
    public static Role create(int businessID, String name) {
        return create(businessID, name, null);
    }
    
    public void init() {
        RoleModel model = getCache().getDAOContext().dao(RoleDAO.class).uniqueByID(getID()).execute();
        _name = model.getName();
    }
    
    public static Role create(int businessID, String name, Group parent) {
        Cache cache = Cache.getCache(businessID);
        Role role = loadByName(cache, name);
        if(role == null) {
            if(parent == null)
                role = new Role(cache, name);
            else
                role = new Role(cache, name, parent);
            RoleDAO dao = role.getDAO(RoleDAO.class);
            role.setID(dao.getNextID());
            dao.add(role.getModel()).enqueue();
            cache.cache(new UID(role), role);
        }
        return role;
    }
    
    @Override
    public String toString() {
        return String.format("[ID:%d Name:%s]", getID(), getName());
    }
}
