package smartshift.business.jersey.objects;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.hibernate.model.GroupModel;

public class GroupJSON {
    @Expose
    public Integer id;
    
    @Expose
    public Integer parentGroupID;
    
    @Expose
    public String name;
    
    @Expose
    public Set<Integer> roles;
    
    public GroupJSON(Group g) {
        id = g.getID();
        name = g.getName();
        Group parent = g.getParent();
        parentGroupID = parent == null ? null : parent.getID();
        roles = new HashSet<Integer>();
        for(Role role:g.getRoles())
        	roles.add(role.getID());
    }
}
