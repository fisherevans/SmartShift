package smartshift.accounts.jersey.providers;

import java.io.IOException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.accounts.security.UserAuthentication;
import smartshift.common.jersey.providers.AbstractAuthFilter;
import smartshift.common.security.BasicAuth;
import smartshift.common.security.session.UserSession;
import smartshift.common.security.session.UserSessionManager;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * Jersey HTTP Basic Auth filter
 * 
 * @author fevans
 */
@Provider
public class UserAuthFilter extends AbstractAuthFilter {
    private static final SmartLogger logger = new SmartLogger(UserAuthFilter.class);

    @Override
    protected void processCredentials(ContainerRequestContext containerRequest, String username, String authString) {
        UserModel user = UserAuthentication.checkAuth(username, authString);
        if(user == null)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        logger.debug("filter() User found");
        containerRequest.setProperty("user", user);
    }
}