package smartshift.accounts.jersey;

import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import com.google.gson.Gson;
import smartshift.accounts.cache.bo.Business;
import smartshift.accounts.cache.bo.User;
import smartshift.accounts.hibernate.dao.BusinessDAO;
import smartshift.accounts.hibernate.dao.ContactMethodDAO;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.accounts.jersey.objects.BusinessJSON;
import smartshift.accounts.jersey.objects.UserFullJSON;
import smartshift.accounts.jersey.objects.UserJSON;
import smartshift.common.jersey.ActionBase;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.params.SimpleIntegerParam;

/**
 * Jersey actions for user methods
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
@Provider
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserActions extends AccountsActionBase {
    private static final SmartLogger logger = new SmartLogger(UserActions.class);

    /**
     * Message to be returned if a business is not found;
     */
    private static final String MSG_204_BUSINESS = "User is not linked to this business or this business does not exist.";

    /**
     * Message to be returned if a contact method is not found;
     */
    private static final String MSG_204_CONTACT_METHOD = "A contact method with this id is not linked to this user.";

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
