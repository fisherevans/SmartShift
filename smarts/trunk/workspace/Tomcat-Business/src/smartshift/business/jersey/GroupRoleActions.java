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
import smartshift.business.updates.types.GroupRoleUpdate;
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
        Group group = getGroup(request.groupID, true);
        logger.debug("addGroupRole() valid group");
        Role role = Role.create(getCache().getBusinessID(), request.roleName, group);
        if(group.hasRole(role))
            return getMessageResponse(Status.OK, "Group already has role");
        logger.debug("addGroupRole() valid role");
        group.addRole(role);
        addUpdate(new GroupRoleUpdate("add", group, role));
        logger.debug("addGroupRole() added");
        return getMessageResponse(Status.OK, "Role added to group.");
    }

    /**
     * @param request the edit request
     * @return the new object
     */
    @POST
    public Response updateGroupRole(EditRequest request) {
        logger.debug("updateGroupRole() Enter");
        Group group = getGroup(request.groupID, true);
        logger.debug("updateGroupRole() valid group");
        Role role = getRole(request.roleID);
        if(!group.hasRole(role))
            return getMessageResponse(Status.BAD_REQUEST, "Group does not have this role.");
        logger.debug("updateGroupRole() valid role");
        Role newRole = role.renameForGroup(group, request.roleName);
        if(request.roleID == newRole.getID()) {
            addUpdate(new GroupRoleUpdate("update", group, newRole));
        } else {
            addUpdate(new GroupRoleUpdate("delete", group, role));
            addUpdate(new GroupRoleUpdate("add", group, newRole));
        }
        logger.debug("updateGroupRole() updated");
        return getObjectResponse(Status.OK, new RoleJSON(newRole));
    }

    /**
     * @param request the delete request
     * @return the message
     */
    @DELETE
    public Response deleteGroupRole(DeleteRequest request) {
        logger.debug("deleteGroupRole() Enter");
        Group group = getGroup(request.groupID, true);
        logger.debug("deleteGroupRole() valid group");
        Role role = getRole(request.roleID);
        if(!group.hasRole(role))
            return getMessageResponse(Status.OK, "Role does not exist for this group");
        logger.debug("deleteGroupRole() valid role");
        group.removeRole(role);
        addUpdate(new GroupRoleUpdate("delete", group, role));
        logger.debug("deleteGroupRole() removed");
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
