package smartshift.business.jersey.objects;

import java.util.List;
import smartshift.common.util.log4j.SmartLogger;

public class GroupRoleRequestJSON {
    private static final SmartLogger logger = new SmartLogger(GroupRoleRequestJSON.class);
    
    private Integer groupID;
    
    private List<String> roleNames;
    
    private List<Integer> roleIDs;
    
    public GroupRoleRequestJSON() {
        
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public List<Integer> getRoleIDs() {
        return roleIDs;
    }

    public void setRoleIDs(List<Integer> roleIDs) {
        this.roleIDs = roleIDs;
    }
}
