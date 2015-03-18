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
import smartshift.business.cache.bo.Group;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.util.GroupRoleEmployeeUtil;
import smartshift.common.util.ValidationUtil;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.params.SimpleIntegerParam;
import com.google.gson.annotations.Expose;

/** @author D. Fisher Evans <contact@fisherevans.com> jersey group actions 
 * jersey actions for groups
 */
@Provider
@Path("/group")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);
    
    /** adds a new group
     * @param request
     * @return the group json object
     */
    @PUT
    public Response addGroup(AddRequest request) {
        String name = ValidationUtil.validateName(request.name);
        if(name == null)
            return getMessageResponse(Status.BAD_REQUEST, "Groups must have a valid name.");
        Group parent = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.parentGroupID, true);
        Group group = Group.create(getCache().getBusinessID(), name, parent);
        return getObjectResponse(Status.OK, new GroupJSON(group));
    }

    /** get a map of groupid -> group objects
     * @param groupIDs the dahs eperated list of grp ids
     * @return the group map
     */
    @GET
    @Path("/{ids}")
    public Response getGroups(@PathParam("ids") String groupIDs) {
        logger.debug("Entering getGroups()");
        Set<Group> groups = new HashSet<Group>();
        StringBuffer error = new StringBuffer();
        String[] ids = groupIDs.split("-");
        for(String id : ids) {
            try {
                Integer intId = new Integer(id);
                Group group = Group.load(getCache(), intId);
                if(group == null)
                    error.append(id + " is an invalid group id. ");
                else
                    groups.add(group);
            } catch(Exception e) {
                error.append(id + " is an invalid integer. ");
            }
        }
        if(error.length() > 0) {
            logger.debug("Errors found in getGroups");
            return getMessageResponse(Status.BAD_REQUEST, error.toString());
        }
        Map<Integer, GroupJSON> groupJsons = new HashMap<>();
        for(Group group : groups)
            groupJsons.put(group.getID(), new GroupJSON(group));
        logger.debug("Returning " + groupJsons.size() + " groups");
        return getObjectResponse(Status.OK, groupJsons);
    }
    
    /**
     * @param request the group edit request
     * @return the new object
     */
    @POST
    public Response editGroup(EditRequest request) {
    	// TODO
        return getMessageResponse(Status.NOT_IMPLEMENTED, "Please try again later.");
    }
    
    /**
     * @param groupID the group to delete
     * @return the message
     */
    @DELETE
    @Path("/{id}")
    public Response deleteGroup(@PathParam("id") SimpleIntegerParam groupID) {
    	// TODO
        return getMessageResponse(Status.NOT_IMPLEMENTED, "Please try again later.");
    }
    
    @SuppressWarnings("javadoc")
    public static class AddRequest {
    	@Expose
        public String name;
    	@Expose
        public Integer parentGroupID;
    	@Override
    	public String toString() {
    		return String.format("[N:%s, PG:%d]", name, parentGroupID);
    	}
    }
    
    @SuppressWarnings("javadoc")
    public static class EditRequest {
    	@Expose
        public Integer id;
    	@Expose
        public String name;
    	@Expose
        public Integer parentGroupID;
    	@Override
    	public String toString() {
    		return String.format("[ID:%d, N:%s, PG:%d]", id, name, parentGroupID);
    	}
    }
}
