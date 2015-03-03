package smartshift.business.jersey.objects;

import java.util.HashSet;
import java.util.Set;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.common.util.log4j.SmartLogger;
import com.google.gson.annotations.Expose;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * json rep of a group
 */
public class GroupJSON {
    private static final SmartLogger logger = new SmartLogger(GroupJSON.class);
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
    public Set<Integer> roleIDs;
    
    /**
     * Initializes the object.
     * @param g the base group
     */
    public GroupJSON(Group g) {
        logger.debug("Creating group JSON for : " + g.getID());
        id = g.getID();
        name = g.getName();
        Group parent = g.getParent();
        parentGroupID = parent == null ? null : parent.getID();
        roleIDs = new HashSet<Integer>();
        for(Role role:g.getRoles()) {
            if(role.getID() < 0)
                continue;
            logger.debug("Add role: " + role.getID() + ":" + role.getName());
        	roleIDs.add(role.getID());
        }
    }
}
