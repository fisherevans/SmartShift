package smartshift.business.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.jersey.objects.RoleJSON;
import smartshift.business.util.GroupRoleEmployeeUtil;
import smartshift.common.util.log4j.SmartLogger;
import com.google.gson.annotations.Expose;

/** @author D. Fisher Evans <contact@fisherevans.com> jersey group actions 
 * jersey actions for groups
 */
@Provider
@Path("/group-role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupRoleActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);
    
    /** adds a list of roles to a group
     * @param request the add request
     * @return the group json object
     */
    @PUT
    public Response addGroupRole(AddRequest request) {
        logger.debug("addGroupRole() Enter");
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.groupID, true);
        Role role = Role.create(getCache().getBusinessID(), request.roleName, group);
        if(group.hasRole(role))
            return getMessageResponse(Status.OK, "Group already has role");
        group.addRole(role);
        return getMessageResponse(Status.OK, "Role added to group.");
    }

    /**
     * @param request the edit request
     * @return the new object
     */
    @POST
    public Response updateGroupRole(EditRequest request) {
        logger.debug("updateGroupRole() Enter");
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.groupID, true);
        Role role = GroupRoleEmployeeUtil.getRole(getCache(), request.roleID);
        if(!group.hasRole(role))
            return getMessageResponse(Status.BAD_REQUEST, "Group does not have this role.");
        Role newRole = getCache().renameGroupRole(group, role, request.roleName);
        return getObjectResponse(Status.OK, new RoleJSON(newRole));
    }

    /**
     * @param request the delete request
     * @return the message
     */
    @DELETE
    public Response deleteGroupRole(DeleteRequest request) {
        logger.debug("deleteGroupRole() Enter");
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.groupID, true);
        Role role = GroupRoleEmployeeUtil.getRole(getCache(), request.roleID);
        if(!group.hasRole(role))
            return getMessageResponse(Status.OK, "Role does not exist for this group");
        getCache().removeGroupRole(group, role);
        return getMessageResponse(Status.OK, "Role removed from group.");
    }

    @SuppressWarnings("javadoc")
    public static class AddRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public String roleName;
    }

    @SuppressWarnings("javadoc")
    public static class DeleteRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer roleID;
    }

    @SuppressWarnings("javadoc")
    public static class EditRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer roleID;
    	@Expose
    	public String roleName;
    }
}
