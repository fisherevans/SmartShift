package smartshift.business.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.cache.bo.Cache;
import smartshift.business.cache.bo.Employee;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.common.util.log4j.SmartLogger;

@Provider
@Path("/org")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BusinessOrgActions extends BusinessActionBase {
    private static final SmartLogger logger = new SmartLogger(BusinessActionBase.class);
    @GET
    @Path("/employee/self")
    public Response userSelf() {
        Employee e = getBusinessCache().getEmployee(getUserSession().employeeID);
        if(e == null) {
            logger.warn("Failed to find employee: " + getUserSession().employeeID);
            return getMessageResponse(Status.INTERNAL_SERVER_ERROR, "Something went wrong...");
        }
        return getObjectResponse(Status.OK, new EmployeeJSON(e));
    }
}
