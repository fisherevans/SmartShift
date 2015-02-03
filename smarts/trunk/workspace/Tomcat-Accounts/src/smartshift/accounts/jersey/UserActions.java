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
import smartshift.accounts.hibernate.dao.BusinessDAO;
import smartshift.accounts.hibernate.dao.ContactMethodDAO;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.accounts.hibernate.model.UserModel;
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
    public Response getUser() {
        logger.debug("UserActions.getUser() Enter");
        return getObjectResponse(Status.OK, getRequestUser());
    }

    /**
     * Gets a map of businesses for the authenticated user <businessID, business>
     * @return HTTP Response with the business map
     */
    @GET
    @Path("/business")
    public Response getUserBusinessesAction() {
//        logger.debug("UserActions.getUserBusinessesAction() Enter");
//        Map<Integer, BusinessModel> businesses = BusinessDAO.getUserBusinessMap(getRequestUser());
//        logger.debug("UserActions.getUserBusinessesAction() found " + businesses.size() + " businesses");
        return getObjectResponse(Status.OK, "businesses");
    }

    /**
     * Gets a business that a user have access to via businessID
     * @param businessID the businessID to look up
     * @return HTTP Response with the business, or a 204 if the user doesn't have access
     */
    @GET
    @Path("/business/{businessID}")
    public Response getUserBusiness(@PathParam("businessID") SimpleIntegerParam businessID) {
//        logger.debug("UserActions.getUserBusiness() Enter");
//        BusinessModel business = BusinessDAO.getUserBusiness(getRequestUser(), businessID.getInteger());
//        if(business == null) {
//            logger.debug("UserActions.getUserBusiness() No business found for id " + businessID.getOriginalValue());
//            return getMessageResponse(Status.NO_CONTENT, MSG_204_BUSINESS);
//        }
        return getObjectResponse(Status.OK, "business");
    }

    /**
     * Gets a map of contact methods for the authenticated user <contactMethodID, contactMethod>
     * @return HTTP Response with the map of contact methods
     */
    @GET
    @Path("/contactMethod")
    public Response getContactMethods() {
//        logger.debug("UserActions.getContactMethods() Enter");
//        Map<Integer, ContactMethodBO> contactMethods = ContactMethodDAO.getUserContactMethodMap(getRequestUser());
        return getObjectResponse(Status.OK, "contactMethods");
    }

    /**
     * Gets a contact method for the authenticated user
     * @param contactMethodID the contact method id to look up
     * @return HTTP Response with the contact method. 204 if not found
     */
    @GET
    @Path("/contactMethod/{contactMethodID}")
    public Response getContactMethod(@PathParam("contactMethodID") SimpleIntegerParam contactMethodID) {
//        logger.debug("UserActions.getUsergetContactMethod() Enter");
//        UserModel user = getRequestUser();
//        ContactMethodBO contactMethod = ContactMethodDAO.getUserContactMethod(user, contactMethodID.getInteger());
//        if(contactMethod == null) {
//            logger.debug("UserActions.getUsergetContactMethod() No countact method found for id " + contactMethodID.getOriginalValue());
//            return getMessageResponse(Status.NO_CONTENT, MSG_204_CONTACT_METHOD);
//        }
        return getObjectResponse(Status.OK, "contactMethod");
    }
}
