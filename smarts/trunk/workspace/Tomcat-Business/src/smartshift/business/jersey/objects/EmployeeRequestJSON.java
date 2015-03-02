package smartshift.business.jersey.objects;

import java.util.List;
import java.util.Map;
import smartshift.common.util.log4j.SmartLogger;
import com.google.gson.annotations.Expose;

public class EmployeeRequestJSON {
    private static final SmartLogger logger = new SmartLogger(EmployeeRequestJSON.class);
    
    @Expose
    private Integer id;
    
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer homeGroupID;

    @Expose
    private Map<Integer, List<Integer>> groupRoleIDs;
    
    public EmployeeRequestJSON() {
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getHomeGroupID() {
        return homeGroupID;
    }

    public void setHomeGroupID(Integer homeGroupID) {
        this.homeGroupID = homeGroupID;
    }

    public Map<Integer, List<Integer>> getGroupRoleIDs() {
        return groupRoleIDs;
    }

    public void setGroupRoleIDs(Map<Integer, List<Integer>> groupRoleIDs) {
        this.groupRoleIDs = groupRoleIDs;
    }
}
