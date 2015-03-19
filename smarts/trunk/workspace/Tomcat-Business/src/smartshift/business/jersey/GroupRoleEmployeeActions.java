package smartshift.business.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.util.GroupRoleEmployeeUtil;
import smartshift.common.util.log4j.SmartLogger;
import com.google.gson.annotations.Expose;

/** @author D. Fisher Evans <contact@fisherevans.com> jersey group actions 
 * jersey actions for groups
 */
@Provider
@Path("/group-role-employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupRoleEmployeeActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);
    
    /**
     * @param request the link request
     * @return the message
     */
    @PUT
    public Response linkGroupRoleEmployee(LinkRequest request) {
        logger.debug("linkGroupRoleEmployee() Enter");
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.groupID, true);
        Role role = Role.load(getCache(), request.roleID);
        if(role == null || !group.hasRole(role))
            return getMessageResponse(Status.BAD_REQUEST, "The group must have this role to add employees to it.");
        Employee employee = GroupRoleEmployeeUtil.getEmployee(getCache(), getEmployee(), request.employeeID, true);
        GroupRoleEmployeeUtil.linkGroupRoleEmployee(getCache(), group, role, employee);
        return getMessageResponse(Status.OK, "The employee was added to the group role.");
    }
    
    /**
     * @param request the unlink request
     * @return the message
     */
    @PUT
    public Response unlinkGroupRoleEmployee(UnlinkRequest request) {
        logger.debug("unlinkGroupRoleEmployee() Enter");
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.groupID, true);
        Role role = GroupRoleEmployeeUtil.getRole(getCache(), request.groupID);
        if(!group.hasRole(role))
            return getMessageResponse(Status.BAD_REQUEST, "The group must have this role to remove employees from it.");
        Employee employee = GroupRoleEmployeeUtil.getEmployee(getCache(), getEmployee(), request.employeeID, true);
        getCache().removeGroupRoleEmployee(group, role, employee);
        return getMessageResponse(Status.OK, "Employee removed from group.");
    }

    @SuppressWarnings("javadoc")
    public static class LinkRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer roleID;
    	@Expose
    	public Integer employeeID;
    }

    @SuppressWarnings("javadoc")
    public static class UnlinkRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer roleID;
    	@Expose
    	public Integer employeeID;
    }
}
