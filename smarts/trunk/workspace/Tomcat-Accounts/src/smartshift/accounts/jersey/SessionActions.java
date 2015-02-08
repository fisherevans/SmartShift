package smartshift.accounts.jersey;

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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.google.gson.annotations.Expose;
import smartshift.accounts.cache.bo.Business;
import smartshift.accounts.cache.bo.User;
import smartshift.accounts.hibernate.dao.SessionDAO;
import smartshift.accounts.hibernate.dao.UserBusinessEmployeeDAO;
import smartshift.accounts.hibernate.model.SessionModel;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.accounts.rmi.BusinessServiceManager;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * Jersey actions for session methods
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
@Provider
@Path("/user/session")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SessionActions extends AccountsActionBase {
    private static final SmartLogger logger = new SmartLogger(SessionActions.class);

    /**
     * Message to be returned if a session is not found;
     */
    private static final String MSG_SESSION_204 = "A session with the given ID does not belong to this business or is not active.";
    
    /**
     * message when there's an error creating a session
     */
    private static final String MSG_SESSION_CREATE_501 = "There was an error creating a session";
    
    /**
     * message when there's not enough data to create a session
     */
    private static final String MSG_SESSION_CREATE_400 = "Please supply a valid employee ID or business ID";
    
    /**
     * message when there's an error creating a session
     */
    private static final String MSG_SESSION_DELETE_501 = "There was an error deleting the session";
    
    /**
     * message when there's an error creating a session
     */
    private static final String MSG_SESSION_DELETE_400 = "Please supply a valid employee ID, business ID and session key";
    
    /**
     * message when there's an error creating a session
     */
    private static final String MSG_SESSION_DELETE_200 = "The session was deleted";

    /**
     * successfully updated a session ts
     */
    private static final String MSG_SESSION_KA_200 = "Session timestamp updated";

    /**
     * message when there's not enough data to lookup a session
     */
    private static final String MSG_SESSION_KA_400 = "Please supply a valid employee ID, business ID and session key";

    /**
     * message when theres an internal error updating a session ts
     */
    private static final String MSG_SESSION_KA_501 = "There was an error updatting the session";

    /**
     * Creates a new session for this user for the given relationship
     * @param sessionRequest the json request for this method
     * @return HTTP Response with the new session object
     */
    @PUT
    public Response createSession(SessionRequest sessionRequest) {
        logger.debug("SessionActions.createSession() Enter");
        if(sessionRequest== null || sessionRequest.businessID == null || sessionRequest.employeeID == null) {
            logger.debug("SessionActions.createSession() Invalid request");
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_CREATE_400);
        }
        User user = getRequestUser();
        Business business = Business.load(sessionRequest.businessID);
        if(user == null || business == null)
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_CREATE_400);
        Integer employeeID = user.getEmployeeID(business);
        if(employeeID == null)
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_CREATE_400);
        
        UserBusinessEmployeeModel ube = UserBusinessEmployeeDAO.getUBE(user.getID(), sessionRequest.businessID, sessionRequest.employeeID);
        if(ube == null)
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_CREATE_400);
        SessionModel session = SessionDAO.createSession(ube.getId());
        if(session == null) {
            logger.debug("SessionActions.createSession() Failed to create session");
            return getObjectResponse(Status.INTERNAL_SERVER_ERROR, MSG_SESSION_CREATE_501);
        }
        for(BusinessServiceInterface bs:BusinessServiceManager.getBusinessServices(sessionRequest.businessID)) {
            try {
                bs.addUserSession(user.getUserName(), session.getSessionKey(), sessionRequest.businessID, AppConstants.SESSION_TIMEOUT);
            } catch(Exception e) {
                logger.warn("Failed to send session to business");
            }
        }
        SessionRequest response = new SessionRequest();
        response.sessionKey = session.getSessionKey();
        return getObjectResponse(Status.OK, response);
    }
    
    /**
     * deletes an existing session for this user
     * @param sessionRequest the json request for this method
     * @return HTTP Response with a message
     */
    @DELETE
    public Response deleteSession(SessionRequest sessionRequest) {
        logger.debug("SessionActions.deleteSession() Enter");
        if(sessionRequest== null || sessionRequest.businessID == null || sessionRequest.employeeID == null || sessionRequest.sessionKey == null) {
            logger.debug("SessionActions.deleteSession() Invalid request");
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_DELETE_400);
        }
        User user = getRequestUser();
        UserBusinessEmployeeModel ube = UserBusinessEmployeeDAO.getUBE(user.getID(), sessionRequest.businessID, sessionRequest.employeeID);
        if(ube == null)
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_CREATE_400);
        SessionModel session = SessionDAO.getSession(ube.getId(), sessionRequest.sessionKey);
        if(session == null) {
            logger.debug("SessionActions.deleteSession() Session not found");
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_DELETE_400);
        }
        if(!SessionDAO.destroySession(session)) {
            logger.debug("SessionActions.deleteSession() Failed to delete session");
            return getMessageResponse(Status.INTERNAL_SERVER_ERROR, MSG_SESSION_DELETE_501);
        }
        return getMessageResponse(Status.OK, MSG_SESSION_DELETE_200);
    }
    
    /**
     * updates the last active timestamp for an existing session for this user
     * @param sessionRequest the json request for this method
     * @return HTTP Response with a message
     */
    @POST
    @Path("/keepAlive")
    public Response keepAliveSession(SessionRequest sessionRequest) {
        logger.debug("SessionActions.keepAliveSession() Enter");
        if(sessionRequest== null || sessionRequest.businessID == null || sessionRequest.employeeID == null || sessionRequest.sessionKey == null) {
            logger.debug("SessionActions.keepAliveSession() Invalid request");
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_KA_400);
        }
        UserBusinessEmployeeModel ube = UserBusinessEmployeeDAO.getUBE(getRequestUser().getID(), sessionRequest.businessID, sessionRequest.employeeID);
        if(ube == null) {
            logger.debug("SessionActions.keepAliveSession() ube not found");
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_204);
        }
        SessionModel session = SessionDAO.getSession(ube.getId(), sessionRequest.sessionKey);
        if(session == null) {
            logger.debug("SessionActions.keepAliveSession() Session not found");
            return getMessageResponse(Status.BAD_REQUEST, MSG_SESSION_204);
        }
        if(!SessionDAO.updateSessionTimestamp(session)) {
            logger.debug("SessionActions.keepAliveSession() Failed to update session");
            return getMessageResponse(Status.INTERNAL_SERVER_ERROR, MSG_SESSION_KA_501);
        }
        return getMessageResponse(Status.OK, MSG_SESSION_KA_200);
    }
    
    /**
     * Object to hold requests for session requests
     * @author D. Fisher Evans <contact@fisherevans.com>
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SessionRequest {
        /**
         * The Business object id
         */
        @Expose
        public Integer businessID;

        /**
         * The Employee object id
         */
        @Expose
        public Integer employeeID;

        /**
         * The session key
         */
        @Expose
        public String sessionKey;
    }
}
