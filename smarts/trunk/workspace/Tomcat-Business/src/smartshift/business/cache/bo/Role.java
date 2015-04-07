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

/**
 * an organizational role
 * @author drew
 */
public class Role extends CachedObject {
    /** the type identifier for a role */
    public static final String TYPE_IDENTIFIER = "R";
    
    private static final SmartLogger logger = new SmartLogger(Role.class);
    
    private String _name;
    private Map<Group, Set<Integer>> _capabilities;
    
    /** the name of the basic role */
    private static String BASIC_ROLE_NAME = "basic";
    /** the id for the basic role */
    private static int BASIC_ROLE_ID = 0;
    
    /** the basic role */
    private static final class BasicRole extends Role {
        
        /** 
         * constructor for a new basic role for the given group 
         * @param cache 
         * @param parent the group to add the role to
         */
        public BasicRole(Cache cache, Group parent) {
            super(cache, BASIC_ROLE_NAME, parent);
        }
        
        /**
         * @see smartshift.business.cache.bo.CachedObject#getID()
         */
        @Override
        public int getID() {
            return BASIC_ROLE_ID;
        }
        
        /**
         * @see smartshift.business.cache.bo.Role#isBasicRole()
         */
        @Override
        public boolean isBasicRole() {
            return true;
        }
    }
    
    /**
     * constructor for a newly created role
     * @param cache
     * @param name the name of the role
     */
    private Role(Cache cache, String name) {
        super(cache);
        _name = name;
        _capabilities = new HashMap<Group, Set<Integer>>();
    }
    
    /**
     * constructor for a newly created role within a group
     * @param cache
     * @param name the name of the role
     * @param parent the group to add the role to
     */
    private Role(Cache cache, String name, Group parent) {
        this(cache, name);
        _capabilities.put(parent, new HashSet<Integer>());
        parent.addRole(this);
    }
    
    /**
     * constructor for a newly loaded role from a model
     * @param cache
     * @param model the model to load from
     */
    private Role(Cache cache, RoleModel model) {
        this(cache, model.getName());
    }
    
    /**
     * constructor for a newly loaded role
     * @param cache
     * @param id
     */
    private Role(Cache cache, int id) {
        super(cache, id);
        _capabilities = new HashMap<Group, Set<Integer>>();
    }

    /**
     * set the role's name
     * @param name the name to set
     */
    protected synchronized void setName(String name) {
        _name = name;
        if(isInitialized())
            getDAO(RoleDAO.class).update(getModel()).enqueue();
    }
    
    /**
     * @return the role's name
     */
    public String getName() {
        return _name;
    }
    
    /**
     * get the basic role for the specified group
     * @param cache
     * @param parent
     * @return the basic role for the specified group
     */
    public static Role getBasicRole(Cache cache, Group parent) {
        Role basicRole = new BasicRole(cache, parent);
        parent.addRole(basicRole);
        return basicRole;
    }
    
    /**
     * @return true if this is a basic role
     */
    public boolean isBasicRole() {
        return false;
    }
    
    /**
     * a new capability has been added to the role within the group
     * @param group
     * @param capabilityID
     */
    public void capabilityAdded(Group group, Integer capabilityID) {
        _capabilities.get(group).add(capabilityID);
    }
    
    /**
     * rename the role for a specific group, will fork the role if necessary
     *   so that other groups using the same role don't have theirs renamed
     * @param group
     * @param newName
     * @return the group with the new name
     */
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
    
    /**
     * fork the role, creating and caching a copy of it
     * @return the new role
     */
    private Role fork() {
        Role newRole = create(getCache().getBusinessID(), getName());
        for(Group g : _capabilities.keySet()) {
            for(Integer c : _capabilities.get(g))
                newRole.capabilityAdded(g, c);
        }
        getCache().cache(new UID(newRole), newRole);
        
        return newRole;
    }
    
    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    public RoleModel getModel() {
        RoleModel model = new RoleModel();
        model.setId(getID());
        model.setName(_name);
        return model;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
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

    /**
     * @see smartshift.common.util.Identifiable#typeCode()
     */
    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    /**
     * load a role into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param roleID the id of the role to load
     * @return the role requested
     */
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
    
    /**
     * load a role by name (never call in the initial load, will cause cycles)
     * @param cache the cache to load into
     * @param roleName the name of the role to load
     * @return the role requested
     */
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
    
    /**
     * create a new role without a group
     * @param businessID the id of the business to create the role for
     * @param name the name of the role
     * @return the new role
     */
    public static Role create(int businessID, String name) {
        return create(businessID, name, null);
    }
    
    /**
     * @see smartshift.business.cache.bo.CachedObject#init()
     */
    @Override
    public void init() {
        super.init();
        RoleModel model = getCache().getDAOContext().dao(RoleDAO.class).uniqueByID(getID()).execute();
        _name = model.getName();
    }
    
    /**
     * create a new role within a group
     * @param businessID the id of the business to create the role for
     * @param name the name of the role
     * @param parent the group to create the role in
     * @return the new role
     */
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
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("[ID:%d Name:%s]", getID(), getName());
    }
}
