package smartshift.business.jersey.objects;

import java.util.HashSet;
import java.util.Set;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import com.google.gson.annotations.Expose;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * json rep of a group
 */
public class GroupJSON {
    /**
     * the group id
     */
    @Expose
    public Integer id;
    
    /**
     * the parent group's id, can be null
     */
    @Expose
    public Integer parentGroupID;
    
    /**
     * the name of the group
     */
    @Expose
    public String name;
    
    /**
     * the role ids within the group
     */
    @Expose
    public Set<Integer> roles;
    
    /**
     * Initializes the object.
     * @param g the base group
     */
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
