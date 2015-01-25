package smartshift.common.jersey.providers;

import java.io.IOException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import smartshift.common.hibernate.model.accounts.UserModel;
import smartshift.common.security.Authentication;
import smartshift.common.security.BasicAuth;
import smartshift.common.security.session.UserSession;
import smartshift.common.security.session.UserSessionManager;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.properties.AppConstants;

/**
 * Jersey HTTP Basic Auth filter
 * 
 * @author fevans
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {
    private static final Logger logger = Logger.getLogger(AuthFilter.class);
    
    private static final int USER_AUTH = 1;
    
    private static final int SESSION_AUTH = 2;
    
    private int _authType = -1;

    /**
     * Filters all requests and requires basic auth
     * 
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException, WebApplicationException {
        logger.debug("AuthFilter.filter() Enter");
        // http://stackoverflow.com/questions/18499465/cors-and-http-basic-auth
        // http://stackoverflow.com/questions/19234892/xmlhttprequest-based-cors-call-with-basic-auth-fails-in-firefox-and-chrome
        // Ignore Options Requests - preflight browsers
        if(containerRequest.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS))
            return;
        logger.debug("AuthFilter.filter() Not a OPTIONS request");
        String auth = containerRequest.getHeaders().getFirst("Authorization");
        if(auth == null)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        logger.debug("AuthFilter.filter() Auth header found");
        String[] authData = BasicAuth.decode(auth);
        if(authData == null || authData.length != 2)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        logger.debug("AuthFilter.filter() valid header value");
        String username = authData[0];
        String authString = authData[1];
        switch(getAuthType()) {
            case USER_AUTH: {
                UserModel user = Authentication.checkAuth(username, authString);
                if(user == null)
                    throw new WebApplicationException(getInvalidCredentialsResponse());
                logger.debug("AuthFilter.filter() User found");
                containerRequest.setProperty("user", user);
                break;
            }
            case SESSION_AUTH: {
                UserSession session = UserSessionManager.getSession(authString, true);
                if(session == null)
                    throw new WebApplicationException(getInvalidCredentialsResponse());
                logger.debug("AuthFilter.filter() Session found");
                containerRequest.setProperty("userSession", session);
                break;
            }
        }
    }


    /**
     * Gets a premade response for invalid credentials
     * @return the response
     */
    public static Response getInvalidCredentialsResponse() {
        logger.debug("AuthFilter.getInvalidCredentialsResponse() Enter");
        return APIResultFactory.getResponse(Status.UNAUTHORIZED, null, "Invalid credentials!");
    }
    
    private int getAuthType() {
        if(_authType == -1) {
            if(AppConstants.AUTH_TYPE.equalsIgnoreCase("user")) {
                logger.info("AuthFilter.getAuthType() Using ---USER--- Authentication");
                _authType = USER_AUTH;
            } else if(AppConstants.AUTH_TYPE.equalsIgnoreCase("session")) {
                logger.info("AuthFilter.getAuthType() Using ---SESSION--- Authentication");
                _authType = SESSION_AUTH;
            }
        }
        return _authType;
    }
}