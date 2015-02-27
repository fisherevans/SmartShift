package smartshift.business.jersey.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.common.util.log4j.SmartLogger;
import com.google.gson.annotations.Expose;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * json representation of an employee
 */
public class EmployeeJSON {
    private static final SmartLogger logger = new SmartLogger(EmployeeJSON.class);
    
    /**
     * the employee id
     */
    @Expose
    public Integer id;
    
    /**
     * the emp's first name
     */
    @Expose
    public String firstName;
    
    /**
     * the emps last name
     */
    @Expose
    public String lastName;

    /**
     * the default/home group id for this emp
     */
    @Expose
    public Integer defaultGroupID;

    /**
     * the map of group ids -> roles they belong to in them
     */
    @Expose
    public Map<Integer, Set<Integer>> groupRoles;
    
    /**
     * Initializes the object.
     * @param e
     */
    public EmployeeJSON(Employee e) {
        this.id = e.getID();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.defaultGroupID = e.getHomeGroup().getID();
    }
    
    /** loads emp info, ecluding group role info
     * @param employee the base employee
     * @return the json rep
     */
    public static EmployeeJSON getSimple(Employee employee) {
        EmployeeJSON employeeJson = new EmployeeJSON(employee);
        return employeeJson;
    }
    
    /** gets the simple employee rep + the group role info
     * @param employee the base employee
     * @return the json rep
     */
    public static EmployeeJSON getFull(Employee employee) {
        logger.debug("Creating employee JSON for: " + employee.getID());
        EmployeeJSON employeeJson = getSimple(employee);
        employeeJson.groupRoles = new HashMap<>();
        for(Group group:employee.getGroups()) {
            Set<Integer> roles = new HashSet<>();
            for(Role role:employee.getRoles(group)) {
                logger.debug("Adding Role: " + role.getID() + ":" + role.getName());
            	roles.add(role.getID());
            }
            employeeJson.groupRoles.put(group.getID(), roles);
        }
        return employeeJson;
    }
}
