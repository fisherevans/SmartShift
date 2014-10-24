package smartshift.common.jersey.filters;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import smartshift.common.hibernate.model.test.WebUser;
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
        String auth = containerRequest.getHeaders().getFirst("Authorization");
        if(auth == null)
            throw APIResultFactory.getException(Status.UNAUTHORIZED);
        String[] authData = BasicAuth.decode(auth);
        if(authData == null || authData.length != 2)
            throw APIResultFactory.getException(Status.UNAUTHORIZED);
        String username = authData[0];
        String password = authData[1];
        WebUser user = Authentication.checkAuth(username, password);
        if(user == null)
            throw APIResultFactory.getException(Status.UNAUTHORIZED);
    }

}