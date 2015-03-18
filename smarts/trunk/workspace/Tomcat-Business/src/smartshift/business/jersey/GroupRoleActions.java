package smartshift.business.jersey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import smartshift.business.jersey.GroupRoleEmployeeActions.UnlinkRequest;
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
@Path("/group-role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupRoleActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);
    
    /** adds a list of roles to a group
     * @param request
     * @return the group json object
     */
    @PUT
    public Response addGroupRole(AddRequest request) {
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.groupID, true);
        Role role = Role.create(getCache().getBusinessID(), request.roleName, group);
        return getObjectResponse(Status.OK, new GroupJSON(group));
    }

    @POST
    public Response updateGroupRole(EditRequest request) {
    	// TODO
        return getMessageResponse(Status.NOT_IMPLEMENTED, "Please try again later.");
    }

    @DELETE
    public Response deleteGroupRole(DeleteRequest request) {
    	// TODO
        return getMessageResponse(Status.NOT_IMPLEMENTED, "Please try again later.");
    }

    public static class AddRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public String roleName;
    }

    public static class DeleteRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer roleID;
    }

    public static class EditRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer roleID;
    	@Expose
    	public String roleName;
    }
}
