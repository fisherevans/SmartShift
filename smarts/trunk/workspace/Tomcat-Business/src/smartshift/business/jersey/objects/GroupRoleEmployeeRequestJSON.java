package smartshift.business.jersey.objects;

import java.util.List;
import smartshift.common.util.log4j.SmartLogger;

public class GroupRoleEmployeeRequestJSON {
    private static final SmartLogger logger = new SmartLogger(GroupRoleEmployeeRequestJSON.class);
    
    private Integer groupID;
    
    private Integer roleID;
    
    private List<Integer> employeeIDs;
    
    public GroupRoleEmployeeRequestJSON() {
        
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public List<Integer> getEmployeeIDs() {
        return employeeIDs;
    }

    public void setEmployeeIDs(List<Integer> employeeIDs) {
        this.employeeIDs = employeeIDs;
    }
}
