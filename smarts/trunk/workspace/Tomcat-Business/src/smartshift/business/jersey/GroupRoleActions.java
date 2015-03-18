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
        //Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.groupID, true);
        //Role role = Role.create(getCache().getBusinessID(), request.roleName, group);
        // TODO
        return getMessageResponse(Status.NOT_IMPLEMENTED, "Please try again later.");
    }

    /**
     * @param request the edit request
     * @return the new object
     */
    @POST
    public Response updateGroupRole(EditRequest request) {
        logger.debug("updateGroupRole() Enter");
    	// TODO
        return getMessageResponse(Status.NOT_IMPLEMENTED, "Please try again later.");
    }

    /**
     * @param request the delete request
     * @return the message
     */
    @DELETE
    public Response deleteGroupRole(DeleteRequest request) {
        logger.debug("deleteGroupRole() Enter");
    	// TODO
        return getMessageResponse(Status.NOT_IMPLEMENTED, "Please try again later.");
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
