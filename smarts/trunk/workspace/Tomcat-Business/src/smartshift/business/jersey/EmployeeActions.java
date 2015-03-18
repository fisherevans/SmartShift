package smartshift.business.jersey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.google.gson.annotations.Expose;

import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.util.GroupRoleEmployeeUtil;
import smartshift.common.util.ValidationUtil;
import smartshift.common.util.collections.ROCollection;
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
    
    /**
     * adds an employee based in a request
     * @param request the add request 
     * @return the employee full response if an employee is created. an error if not
     */
    @PUT
    public Response addEmployee(AddRequest request) {
    	logger.debug("addEmployee() Enter " + request);
        if(ValidationUtil.validateName(request.firstName) == null)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must have a valid first name.");
        if(ValidationUtil.validateName(request.lastName) == null)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must have a valid last name.");
    	logger.debug("addEmployee() valid names");
        Group proposedHomeGroup = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.homeGroupID, true);
        if(request.groupRoleIDs == null || request.groupRoleIDs.size() == 0)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must be in a group and role.");
        Map<Group, List<Role>> groupRoles = new HashMap<>();
        boolean validHomeGroup = false;
        for(Integer groupID:request.groupRoleIDs.keySet()) {
            Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), groupID, true);
            if(group.getID() == proposedHomeGroup.getID())
                validHomeGroup = true;
            List<Role> roles = GroupRoleEmployeeUtil.getRolesByIDs(getCache(), request.groupRoleIDs.get(groupID));
            if(roles.size() == 0)
                return getMessageResponse(Status.BAD_REQUEST, "An employee cannot be in a group, but not in a role. " + groupID);
            groupRoles.put(group, roles);
        }
        if(!validHomeGroup)
            return getMessageResponse(Status.BAD_REQUEST, "The default group:" + request.homeGroupID + " must be a group the employee is initially added to.");
    	logger.debug("addEmployee() valid group roles and home group");
        Employee newEmployee = Employee.create(getCache().getBusinessID(), request.firstName, request.lastName, proposedHomeGroup.getID());
    	logger.debug("addEmployee() employee created");
        for(Group group:groupRoles.keySet())
            for(Role role:groupRoles.get(group))
                GroupRoleEmployeeUtil.linkGroupRoleEmployee(getCache(), group, role, newEmployee);
    	logger.debug("addEmployee() group roles added");
        return getObjectResponse(Status.ACCEPTED, new EmployeeJSON(newEmployee));
    }
    
    /** gets simple employee info
     * @param employeeID the employee id from the url
     * @return the employee json object
     */
    @GET
    @Path("/{id}")
    public Response getEmployee(@PathParam("id") SimpleIntegerParam employeeID) {
    	logger.debug("getEmployee() Enter " + employeeID.getInteger());
        Employee employee = Employee.load(getCache(), employeeID.getInteger());
        if(employee == null)
            return getMessageResponse(Status.BAD_REQUEST, "Invalid employee id:" + employeeID);
    	logger.debug("getEmployee() got employee");
        return getObjectResponse(Status.OK, new EmployeeJSON(employee));
    }
    
    @POST
    @Path("/{id}")
    public Response editEmployee(@PathParam("id") SimpleIntegerParam employeeID, EditRequest request) {
    	logger.debug("editEmployee() Enter " + employeeID.getInteger() + " - " + request);
        Employee employee = GroupRoleEmployeeUtil.getEmployee(getCache(), getEmployee(), employeeID.getInteger(), true);
        if(request.firstName != null && ValidationUtil.validateName(request.firstName) == null)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must have a valid first name.");
        if(request.lastName != null && ValidationUtil.validateName(request.lastName) == null)
            return getMessageResponse(Status.BAD_REQUEST, "Employees must have a valid last name.");
        Group newHomeGroup = null;
        if(request.homeGroupID != null) {
        	newHomeGroup = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.homeGroupID, true);
        	if(!GroupRoleEmployeeUtil.isValidHomeGroup(employee, newHomeGroup))
                return getMessageResponse(Status.BAD_REQUEST, "The employee must be a member of the home group");
        }
    	logger.debug("editEmployee() Valid data");
        if(request.firstName != null) employee.setFirstName(request.firstName);
        if(request.lastName != null) employee.setLastName(request.lastName);
        if(newHomeGroup != null) employee.setHomeGroup(newHomeGroup);
    	logger.debug("editEmployee() employee updated");
        return getObjectResponse(Status.ACCEPTED, new EmployeeJSON(employee));
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") SimpleIntegerParam employeeID) {
    	logger.debug("deleteEmployee() Enter " + employeeID.getInteger());
        Employee employee = Employee.load(getCache(), employeeID.getInteger());
        if(employee == null)
            return getMessageResponse(Status.BAD_REQUEST, "Invalid employee id:" + employeeID);
    	logger.debug("deleteEmployee() got employee");
        getCache().deleteEmployee(employee);
    	logger.debug("deleteEmployee() employee deleted");
        return getMessageResponse(Status.OK, "The employee was deleted");
    }
    
    public static class EditRequest {
        @Expose
        public String firstName;
        @Expose
        public String lastName;
        @Expose
        public Integer homeGroupID;
        @Override
        public String toString() {
        	return String.format("[FN:%s, LS:%s, HG:%d]", firstName, lastName, homeGroupID);
        }
    }
    
    public static class AddRequest {
        @Expose
        public String firstName;
        @Expose
        public String lastName;
        @Expose
        public Integer homeGroupID;
        @Expose
        public Map<Integer, List<Integer>> groupRoleIDs;
        @Override
        public String toString() {
        	return String.format("[FN:%s, LS:%s, HG:%d, GR:%d]", firstName, lastName, homeGroupID, groupRoleIDs == null ? null : groupRoleIDs.size());
        }
    }
}
