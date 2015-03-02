package smartshift.business.jersey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.jersey.objects.EmployeeRequestJSON;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.util.GroupRoleEmployeeUtil;
import smartshift.common.util.ValidationUtil;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.params.SimpleIntegerParam;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * jersey actions for employees
 */
@Provider
@Path("/employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);

    private static final String INVALID_GROUP = "Either you do now manage this group, or it does not exist: ";
    private static final String INVALID_GROUP_ROLE = "The group %d does not contain the role: %d";
    
    /** gets simple employee info
     * @param employeeID the employee id from the url
     * @return the employee json object
     */
    @GET
    @Path("/{id}")
    public Response simple(@PathParam("id") SimpleIntegerParam employeeID) {
        Employee employee = Employee.load(getCache(), employeeID.getInteger());
        if(employee == null) {
            logger.warn("Failed to find employee: " + getUserSession().employeeID);
            return getMessageResponse(Status.INTERNAL_SERVER_ERROR, "Something went wrong...");
        }
        return getObjectResponse(Status.OK, EmployeeJSON.getSimple(employee));
    }
    
    /** gets an employee object with the groups and roles they belong to
     * @param employeeID the employee id from the ur;
     * @return  the employee json object
     */
    @GET
    @Path("/full/{id}")
    public Response full(@PathParam("id") SimpleIntegerParam employeeID) {
    	Employee employeeSelf = getEmployee();
        Employee employeeLookup = Employee.load(getCache(), employeeID.getInteger());
        if(employeeLookup == null)
    		return getMessageResponse(Status.BAD_REQUEST, "This employee does not exist.");
    	if(employeeSelf.getID() == employeeLookup.getID() || employeeSelf.manages(employeeLookup))
            return getObjectResponse(Status.OK, EmployeeJSON.getFull(employeeLookup));
    	else
    		return getMessageResponse(Status.BAD_REQUEST, "You can not manage this emplpoyee.");
    }
    
    /**
     * adds an employee based in a request
     * @param request the add request 
     * @return the employee full response if an employee is created. an error if not
     */
    @PUT
    public Response addEmployee(EmployeeRequestJSON request) {
        String firstName = ValidationUtil.validateName(request.getFirstName());
        String lastName = ValidationUtil.validateName(request.getLastName());
        if(firstName == null || lastName == null)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must have a valid first and last name.");
        Integer homeGroupID = request.getHomeGroupID();
        Group homeGroup = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), homeGroupID, true);
        if(homeGroup == null)
            return getMessageResponse(Status.BAD_REQUEST, INVALID_GROUP + homeGroupID);
        Map<Group, List<Role>> groupRoles = new HashMap<>();
        Map<Integer, List<Integer>> groupRoleIDs = request.getGroupRoleIDs();
        if(groupRoleIDs != null) {
            for(Integer groupID:groupRoleIDs.keySet()) {
                Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), groupID, true);
                if(group == null)
                    return getMessageResponse(Status.BAD_REQUEST, INVALID_GROUP + groupID);
                List<Role> roles = new ArrayList<>();
                for(Integer roleID:groupRoleIDs.get(groupID)) {
                    Role role = Role.load(getCache(), roleID);
                    if(!group.hasRole(role))
                        return getMessageResponse(Status.BAD_REQUEST, String.format(INVALID_GROUP_ROLE, groupID, roleID));
                    roles.add(role);
                }
                groupRoles.put(group, roles);
            }
        }
        Employee newEmployee = Employee.create(getCache().getBusinessID(), firstName, lastName, homeGroup.getID());
        for(Group group:groupRoles.keySet()) {
            for(Role role:groupRoles.get(group)) {
                newEmployee.addRole(role, group);
            }
        }
        return getObjectResponse(Status.ACCEPTED, EmployeeJSON.getFull(newEmployee));
    }
}
