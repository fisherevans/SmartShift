package smartshift.accounts.jersey.providers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;
import smartshift.accounts.cache.bo.User;
import smartshift.accounts.security.UserAuthentication;
import smartshift.common.jersey.providers.AbstractAuthFilter;
import smartshift.common.util.log4j.SmartLogger;

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
        User user = UserAuthentication.checkAuth(username, authString);
        logger.debug("filter() User = " + (user == null ? "NULL" : username));
        if(user == null)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        containerRequest.setProperty("user", user);
    }
}