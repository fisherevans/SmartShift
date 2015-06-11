package smartshift.business.jersey.objects;

import java.util.List;
import java.util.Map;
import smartshift.business.cache.bo.Employee;
import com.google.gson.annotations.Expose;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * json representation of an employee
 */
public class EmployeeJSON {
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
    public Integer homeGroupID;
    
    /**
     * map of groupIDs to roleIDs
     */
    @Expose
    public Map<Integer, List<Integer>> groupRoleIDs;
    
    /**
     * Initializes the object.
     * @param e
     */
    public EmployeeJSON(Employee e) {
        this.id = e.getID();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.homeGroupID = e.getHomeGroup() == null ? null : e.getHomeGroup().getID();
    }
}
