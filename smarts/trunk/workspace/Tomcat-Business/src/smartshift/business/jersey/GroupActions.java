package smartshift.business.jersey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.cache.bo.Group;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.common.util.log4j.SmartLogger;

/** @author D. Fisher Evans <contact@fisherevans.com> jersey group actions 
 * jersey actions for groups
 */
@Provider
@Path("/group")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupActions extends BusinessActionBase {
    private static final SmartLogger logger = new SmartLogger(BusinessActionBase.class);

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
                Group group = Group.load(getBusinessCache(), intId);
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
}
