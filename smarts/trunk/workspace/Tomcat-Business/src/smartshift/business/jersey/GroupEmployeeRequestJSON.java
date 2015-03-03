package smartshift.business.jersey;

import java.util.List;
import smartshift.common.util.log4j.SmartLogger;

public class GroupEmployeeRequestJSON {
    private static final SmartLogger logger = new SmartLogger(GroupEmployeeRequestJSON.class);
    
    private Integer groupID;
    
    private List<Integer> employeeIDs;
    
    public GroupEmployeeRequestJSON() {
        
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public List<Integer> getEmployeeIDs() {
        return employeeIDs;
    }

    public void setEmployeeIDs(List<Integer> employeeIDs) {
        this.employeeIDs = employeeIDs;
    }
}
