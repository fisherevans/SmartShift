package smartshift.business.util;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import smartshift.business.cache.bo.Cache;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.common.jersey.BaseActions;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Utility methods for access and controlling GRE objects
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
public class GroupRoleEmployeeUtil {
    private static final SmartLogger logger = new SmartLogger(GroupRoleEmployeeUtil.class);
    
    /**
     * Gets a Group BO
     * @param cache The cache to use
     * @param employee the employee requesting access
     * @param groupID the group id
     * @param requireManages set true if should return null if group is not managed by employee
     * @return the group. null if invalid id. null if requireManages is true and employee does not manage it
     */
    public static Group getGroup(Cache cache, Employee employee, Integer groupID, boolean requireManages) {
        logger.debug("getGroup() Enter");
        Group group = Group.load(cache, groupID);
        if(group == null || (requireManages && !employee.manages(group))) {
            String error = "The group does not exist" + (requireManages ? ", or you do not manage the group":"") + ": " + groupID;
            throw new WebApplicationException(BaseActions.getMessageResponse(Status.BAD_REQUEST, error));
        }
        logger.debug("getGroup() Valid group found");
        return group;
    }
    
    /**
     * @param cache
     * @param self the employee loading the employees
     * @param employeeIDs the ids to look up
     * @return the list of employee bo's
     * @throws WebApplicationException if any of the emp ids are invalid, or the caller does not manage one of the employees
     */
    public static List<Employee> getEmployeesFromIDs(Cache cache, Employee self, List<Integer> employeeIDs) {
        if(employeeIDs == null || employeeIDs.size() == 0)
            throw new WebApplicationException(BaseActions.getMessageResponse(Status.BAD_REQUEST, "You must pass employees to add."));
        List<Employee> employees = new ArrayList<>();
        for(Integer employeeID:employeeIDs) {
            Employee employee = Employee.load(cache, employeeID);
            if(employee == null || !self.manages(employee))
                throw new WebApplicationException(BaseActions.getMessageResponse(Status.BAD_REQUEST, "You  do not manage the employee: " + employeeID));
            employees.add(employee);
        }
        return employees;
    }
    
    /**
     * @param chache
     * @param group the group to add the role to
     * @param role the role to link to
     */
    public static void linkGroupRole(Cache chache, Group group, Role role) {
        group.addRole(role);
    }
    
    /**
     * @param chache
     * @param group the group to add the employee to
     * @param employee the employee to link to
     */
    public static void linkGroupEmployee(Cache chache, Group group, Employee employee) {
        group.addEmployee(employee);
        employee.addGroup(group);
    }
    
    /**
     * links a group role, and group employee - then links the group role employee.
     * @param cache
     * @param group the group to add the employee to in the role
     * @param role the role to add the employee to
     * @param employee the employee to link tot he group role
     */
    public static void linkGroupRoleEmployee(Cache cache, Group group, Role role, Employee employee) {
        linkGroupRole(cache, group, role);
        linkGroupEmployee(cache, group, employee);
        group.addEmployeeRole(employee, role);
        employee.addGroupRole(role, group);
    }
}
