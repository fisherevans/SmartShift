package smartshift.business.jersey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public Response getEmployee(@PathParam("id") SimpleIntegerParam employeeID) {
        Employee employee = Employee.load(getCache(), employeeID.getInteger());
        if(employee == null)
            return getMessageResponse(Status.BAD_REQUEST, "Invalid employee id:" + employeeID);
        return getObjectResponse(Status.OK, new EmployeeJSON(employee));
    }
    
//    /** gets an employee object with the groups and roles they belong to
//     * @param employeeID the employee id from the ur;
//     * @return  the employee json object
//     */
//    @GET
//    @Path("/full/{id}")
//    public Response full(@PathParam("id") SimpleIntegerParam employeeID) {
//    	Employee employeeSelf = getEmployee();
//        Employee employeeLookup = Employee.load(getCache(), employeeID.getInteger());
//        if(employeeLookup == null)
//    		return getMessageResponse(Status.BAD_REQUEST, "This employee does not exist.");
//    	if(employeeSelf.getID() == employeeLookup.getID() || employeeSelf.manages(employeeLookup))
//            return getObjectResponse(Status.OK, EmployeeJSON.getFull(employeeLookup));
//    	else
//    		return getMessageResponse(Status.BAD_REQUEST, "You can not manage this emplpoyee.");
//    }
    
    @POST
    public Response editEmployee(EmployeeJSON request) {
        Employee employee = GroupRoleEmployeeUtil.getEmployee(getCache(), getEmployee(), request.id, true);
        String firstName = ValidationUtil.validateName(request.firstName);
        String lastName = ValidationUtil.validateName(request.lastName);
        if(firstName == null || lastName == null)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must have a valid first and last name.");
        Group homeGroup = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.homeGroupID, true);
        boolean validHomeGroup = false;
        for(Group group:employee.getGroups())
            if(group.getID() == homeGroup.getID())
                validHomeGroup = true;
        if(!validHomeGroup)
            return getMessageResponse(Status.BAD_REQUEST, "The employee must be a member of the home group");
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setHomeGroup(homeGroup);
        return getObjectResponse(Status.ACCEPTED, new EmployeeJSON(employee));
    }
    
    /**
     * adds an employee based in a request
     * @param request the add request 
     * @return the employee full response if an employee is created. an error if not
     */
    @PUT
    public Response addEmployee(EmployeeJSON request) {
        String firstName = ValidationUtil.validateName(request.firstName);
        String lastName = ValidationUtil.validateName(request.lastName);
        if(firstName == null || lastName == null)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must have a valid first and last name.");
        if(request.groupRoleIDs == null || request.groupRoleIDs.size() == 0)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must be in a group and role.");
        Group homeGroup = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.homeGroupID, true);
        Map<Group, List<Role>> groupRoles = new HashMap<>();
        boolean validHomeGroup = false;
        for(Integer groupID:request.groupRoleIDs.keySet()) {
            Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), groupID, true);
            if(group.getID() == homeGroup.getID())
                validHomeGroup = true;
            groupRoles.put(group, GroupRoleEmployeeUtil.getRolesByIDs(getCache(), request.groupRoleIDs.get(groupID)));
        }
        if(!validHomeGroup)
            return getMessageResponse(Status.BAD_REQUEST, "The default group:" + request.homeGroupID + " must be a group the employee is in.");
        Employee newEmployee = Employee.create(getCache().getBusinessID(), firstName, lastName, homeGroup.getID());
        for(Group group:groupRoles.keySet())
            for(Role role:groupRoles.get(group))
                GroupRoleEmployeeUtil.linkGroupRoleEmployee(getCache(), group, role, newEmployee);
        return getObjectResponse(Status.ACCEPTED, new EmployeeJSON(newEmployee));
    }
}
