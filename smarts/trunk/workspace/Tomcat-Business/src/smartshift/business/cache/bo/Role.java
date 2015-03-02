package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.RoleDAO;
import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.util.UID;
import smartshift.common.util.log4j.SmartLogger;

public class Role extends CachedObject {
    public static final String TYPE_IDENTIFIER = "R";
    
    private static final SmartLogger logger = new SmartLogger(Role.class);
    
    private String _name;
    private Map<Group, Set<Capability>> _capabilities;
    
    private RoleModel _model;
    
    private Role(Cache cache, String name) {
        super(cache);
        _name = name;
        _capabilities = new HashMap<Group, Set<Capability>>();
    }
    
    public Role(Cache cache, String name, Group parent) {
        this(cache, name);
        _capabilities.put(parent, new HashSet<Capability>());
    }
    
    private Role(Cache cache, RoleModel model) {
        this(cache, model.getName());
        _model = model;
        loadAllChildren();
    }
    
    public String getName() {
        return _name;
    }
    
    public static Role getBasicRole(Cache cache, Group parent) {
        Role basicRole = new Role(cache, "basic", parent);
        if(!parent.hasRole(basicRole))
            parent.addRole(basicRole);
        return basicRole;
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setName(_name);
                getDAO(RoleDAO.class).update(_model);
            } else {
                _model = getDAO(RoleDAO.class).add(_name).execute();
            }
        } catch (HibernateException e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void loadAllChildren() {
        // do nothing
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
    
    public static Role load(Cache cache, int roleID) {
        UID uid = new UID(TYPE_IDENTIFIER, roleID);
        if(cache.contains(uid))
            return cache.getRole(roleID); 
        else {
            RoleModel model = cache.getDAOContext().dao(RoleDAO.class).uniqueByID(roleID).execute();
            Role role = null;
            if(model != null) {
                cache.cache(uid, null);
                role = new Role(cache, model);
                cache.cache(uid, role);
            }
            return role;
        }
    }
    
    public static Role loadByName(Cache cache, String roleName) {
        // TODO - Need to be abke to get the Role based on a name
        return null;
    }
    
    public static Role create(int businessID, String name) {
        return create(businessID, name, null);
    }
    
    public static Role create(int businessID, String name, Group parent) {
        Cache cache = Cache.getCache(businessID);
        Role role;
        if(parent == null)
            role = new Role(cache, name);
        else
            role = new Role(cache, name, parent);
        role.save();
        cache.cache(new UID(role), role);
        return role;
    }
}
