package smartshift.common.cache.bo.business;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import smartshift.common.cache.bo.accounts.Business;
import smartshift.common.hibernate.model.business.RoleModel;
import smartshift.common.util.hibernate.Stored;

public class Role extends CachedObject implements Stored{
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
    
    public String getName(String name) {
        return _name;
    }
    
    public static Role getBasicRole(Cache cache, Group parent) {
        Role basicRole = new Role(cache, "basic", parent);
        if(!parent.hasRole(basicRole))
            parent.addRole(basicRole);
        return basicRole;
    }

    public void save() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }
    
}
