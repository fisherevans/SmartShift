package smartshift.accounts.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.accounts.cache.bo.Business;
import smartshift.accounts.cache.bo.User;
import smartshift.accounts.jersey.objects.BusinessJSON;
import smartshift.accounts.jersey.objects.UserFullJSON;
import smartshift.accounts.jersey.objects.UserJSON;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Jersey actions for user methods
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
@Provider
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserActions extends BaseAccountActions {
    private static final SmartLogger logger = new SmartLogger(UserActions.class);

    /**
     * Gets the basic information for the authenticated user
     * @return HTTP Response with the user object
     */
    @GET
    @Path("/self")
    public Response userSelf() {
        logger.debug("UserActions.userSelf() Enter");
        return getObjectResponse(Status.OK, new UserJSON(getRequestUser()));
    }

    /**
     * Gets the full information for the authenticated user
     * included businesses and employee ids
     * @return HTTP Response with the full user object
     */
    @GET
    @Path("/full")
    public Response userFull() {
        logger.debug("UserActions.userFull() Enter");
        User user = getRequestUser();
        UserFullJSON userFull = new UserFullJSON();
        for(Business business:user.getBusinesses()) {
            BusinessJSON businessJson = new BusinessJSON(business);
            businessJson.employeeID = user.getEmployeeID(business);
            userFull.businesses.add(businessJson);
        }
        userFull.user = new UserJSON(getRequestUser());
        return getObjectResponse(Status.OK, userFull);
    }
}
