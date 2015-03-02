package smartshift.business.jersey;

import java.util.List;
import smartshift.common.util.log4j.SmartLogger;

public class GroupEmployeeRequestJSON {
    private static final SmartLogger logger = new SmartLogger(GroupEmployeeRequestJSON.class);
    
    private Integer group;
    
    private List<Integer> employees;
    
    public GroupEmployeeRequestJSON() {
        
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public List<Integer> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Integer> employees) {
        this.employees = employees;
    }
}
