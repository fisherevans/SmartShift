package smartshift.business.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import smartshift.business.updates.types.GroupRoleEmployeeUpdate;
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
        Group group = getGroup(request.groupID, true);
        logger.debug("linkGroupRoleEmployee() valid group");
        Role role = Role.load(getCache(), request.roleID);
        if(role == null || !group.hasRole(role))
            return getMessageResponse(Status.BAD_REQUEST, "The group must have this role to add employees to it.");
        logger.debug("linkGroupRoleEmployee() valid role");
        Employee employee = getEmployee(request.employeeID, true);
        logger.debug("linkGroupRoleEmployee() valid employee");
        group.addRoleEmployee(role, employee);
        addUpdate(new GroupRoleEmployeeUpdate("add", group, role, employee));
        logger.debug("linkGroupRoleEmployee() linked");
        return getMessageResponse(Status.OK, "The employee was added to the group role.");
    }
    
    /**
     * @param request the unlink request
     * @return the message
     */
    @DELETE
    public Response unlinkGroupRoleEmployee(UnlinkRequest request) {
        logger.debug("unlinkGroupRoleEmployee() Enter");
        if(request.employeeID == getRequestEmployee().getID())
            return getMessageResponse(Status.BAD_REQUEST, "You cannot remove yourself from a role.");
        Group group = getGroup(request.groupID, true);
        logger.debug("unlinkGroupRoleEmployee() valid group");
        Role role = getRole(request.roleID);
        if(!group.hasRole(role))
            return getMessageResponse(Status.BAD_REQUEST, "The group must have this role to remove employees from it.");
        logger.debug("unlinkGroupRoleEmployee() valid role");
        Employee employee = getEmployee(request.employeeID, true);
        logger.debug("unlinkGroupRoleEmployee() valid employee");
        group.removeRoleEmployee(role, employee);
        addUpdate(new GroupRoleEmployeeUpdate("delete", group, role, employee));
        logger.debug("unlinkGroupRoleEmployee() unlinked");
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
