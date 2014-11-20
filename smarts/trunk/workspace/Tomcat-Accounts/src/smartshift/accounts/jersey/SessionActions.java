package smartshift.accounts.jersey;

import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import smartshift.common.bo.ContactMethodBO;
import smartshift.common.hibernate.dao.accounts.BusinessDAO;
import smartshift.common.hibernate.dao.accounts.ContactMethodDAO;
import smartshift.common.hibernate.model.accounts.Business;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.jersey.ActionBase;
import smartshift.common.util.params.SimpleIntegerParam;

/**
 * Jersey actions for session methods
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
@Provider
@Path("/user/session")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SessionActions extends ActionBase {
    private static final Logger logger = Logger.getLogger(SessionActions.class);

    /**
     * Message to be returned if a session is not found;
     */
    private static final String MSG_204_SESSION = "A session with the given ID does not belong to this business or is not active.";

    /**
     * Gets the basic information for the authenticated user
     * @return HTTP Response with the user object
     */
    @PUT
    @Path("/{businessID}")
    public Response createSession(@PathParam("businessID") SimpleIntegerParam businessID) {
        logger.debug("SessionActions.createSession() Enter");
        return getObjectResponse(Status.OK, getRequestUser());
    }
}
