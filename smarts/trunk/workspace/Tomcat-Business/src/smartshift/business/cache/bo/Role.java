package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.dao.RoleDAO;
import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.util.UID;
import smartshift.common.util.log4j.SmartLogger;

public class Role extends CachedObject {
    public static final String TYPE_IDENTIFIER = "R";
    
    private static final SmartLogger logger = new SmartLogger(Role.class);
    
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
    
    private final String _name;
    private final Map<Group, Set<Capability>> _capabilities;
    
    private RoleModel _model;
    
    private Role(Cache cache, String name) {
        super(cache);
        _name = name;
        _capabilities = new HashMap<Group, Set<Capability>>();
    }
    
    public Role(Cache cache, String name, Group parent) {
        this(cache, name);
        _capabilities.put(parent, new HashSet<Capability>());
        parent.addRole(this);
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
        Role basicRole = new BasicRole(cache, parent);
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
                //cache.cache(uid, new PlaceHolderObject(cache, TYPE_IDENTIFIER, roleID));
                cache.cache(uid, null);
                role = new Role(cache, model);
                cache.cache(uid, role);
            }
            return role;
        }
    }
    
    public static Role loadByName(Cache cache, String roleName) {
        Role role = cache.getRole(roleName);
        if(role == null) {
            RoleModel model = cache.getDAOContext().dao(RoleDAO.class).uniqueByCriteria(Restrictions.eq("name", roleName)).execute();
            if(model != null) {
                UID uid = new UID(TYPE_IDENTIFIER, model.getId());
                cache.cache(uid, null);
                role = new Role(cache, model);
                cache.cache(uid, role);
            }
        }
        return role;
    }
    
    public static Role create(int businessID, String name) {
        return create(businessID, name, null);
    }
    
    public static Role create(int businessID, String name, Group parent) {
        Cache cache = Cache.getCache(businessID);
        Role role = loadByName(cache, name);
        if(role == null) {
            if(parent == null)
                role = new Role(cache, name);
            else
                role = new Role(cache, name, parent);
            role.save();
            cache.cache(new UID(role), role);
        }
        return role;
    }
}
