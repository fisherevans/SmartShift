package smartshift.business.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.updates.types.GroupEmployeeUpdate;
import smartshift.common.util.log4j.SmartLogger;
import com.google.gson.annotations.Expose;

/** @author D. Fisher Evans <contact@fisherevans.com> jersey group actions 
 * jersey actions for groups
 */
@Provider
@Path("/group-employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupEmployeeActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);

    /** get a map of groupid -> group objects
     * @param request the remove request
     * @return the group map
     */
    @DELETE
    public Response removeGroupEmployee(RemoveRequest request) {
        logger.debug("removeGroupEmployee() Enter");
        Group group = getGroup(request.groupID, true);
        logger.debug("removeGroupEmployee() valid group");
        Employee employee = getEmployee(request.employeeID, true);
        logger.debug("removeGroupEmployee() valid employee");
        group.removeEmployee(employee);
        addUpdate(new GroupEmployeeUpdate("delete", group, employee));
        logger.debug("removeGroupEmployee() removed");
        return getMessageResponse(Status.OK, "Employee removed from group.");
    }
    
    @SuppressWarnings("javadoc")
    public static class RemoveRequest {
    	@Expose
    	public Integer groupID;
    	@Expose
    	public Integer employeeID;
    }
}
