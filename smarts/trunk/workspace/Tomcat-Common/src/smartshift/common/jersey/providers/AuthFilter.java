package smartshift.common.jersey.providers;

import java.io.IOException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.security.Authentication;
import smartshift.common.security.BasicAuth;
import smartshift.common.util.json.APIResultFactory;

/**
 * Jersey HTTP Basic Auth filter
 * 
 * @author fevans
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    /**
     * Filters all requests and requires basic auth
     * 
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException, WebApplicationException {
        logger.debug("AuthFilter.filter()");
        // http://stackoverflow.com/questions/18499465/cors-and-http-basic-auth
        // http://stackoverflow.com/questions/19234892/xmlhttprequest-based-cors-call-with-basic-auth-fails-in-firefox-and-chrome
        // Ignore Options Requests - preflight browsers
        if(containerRequest.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS))
            return;
        String auth = containerRequest.getHeaders().getFirst("Authorization");
        if(auth == null)
            throw new WebApplicationException(APIResultFactory.getInvalidCredentialsResponse());
        String[] authData = BasicAuth.decode(auth);
        if(authData == null || authData.length != 2)
            throw new WebApplicationException(APIResultFactory.getInvalidCredentialsResponse());
        String username = authData[0];
        String password = authData[1];
        User user = Authentication.checkAuth(username, password);
        if(user == null)
            throw new WebApplicationException(APIResultFactory.getInvalidCredentialsResponse());
        containerRequest.setProperty("user", user);
    }

}