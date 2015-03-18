package smartshift.business.jersey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import com.google.gson.annotations.Expose;

import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.jersey.objects.GroupRequestJSON;
import smartshift.business.jersey.objects.GroupRoleEmployeeRequestJSON;
import smartshift.business.jersey.objects.GroupRoleRequestJSON;
import smartshift.business.util.GroupRoleEmployeeUtil;
import smartshift.common.util.ValidationUtil;
import smartshift.common.util.log4j.SmartLogger;

/** @author D. Fisher Evans <contact@fisherevans.com> jersey group actions 
 * jersey actions for groups
 */
@Provider
@Path("/group-role-employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupRoleEmployeeActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);
    
    @PUT
    public Response linkGroupRoleEmployee(LinkRequest request) {
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.groupID, true);
        Role role = Role.load(getCache(), request.roleID);
        if(role == null || !group.hasRole(role))
            return getMessageResponse(Status.BAD_REQUEST, "The group must have this role to add employees to it.");
        Employee employee = GroupRoleEmployeeUtil.getEmployee(getCache(), getEmployee(), request.employeeID, true);
        GroupRoleEmployeeUtil.linkGroupRoleEmployee(getCache(), group, role, employee);
        return getMessageResponse(Status.OK, "The employee was added to the group role.");
    }
    
    @PUT
    public Response unlinkGroupRoleEmployee(UnlinkRequest request) {
    	// TODO
        return getMessageResponse(Status.NOT_IMPLEMENTED, "Please try again later.");
    }

    public static class LinkRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer roleID;
    	@Expose
    	public Integer employeeID;
    }

    public static class UnlinkRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer roleID;
    	@Expose
    	public Integer employeeID;
    }
}
