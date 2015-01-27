package smartshift.business.jersey.providers;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;
import smartshift.common.jersey.providers.AbstractAuthFilter;
import smartshift.common.security.session.UserSession;
import smartshift.common.security.session.UserSessionManager;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Jersey HTTP Basic Auth filter
 * 
 * @author fevans
 */
@Provider
public class SessionAuthFilter extends AbstractAuthFilter {
    private static final SmartLogger logger = new SmartLogger(SessionAuthFilter.class);

    @Override
    protected void processCredentials(ContainerRequestContext containerRequest, String username, String authString) {
        UserSession session = UserSessionManager.getSession(authString, true);
        if(session == null)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        logger.debug("filter() Session found");
        containerRequest.setProperty("userSession", session);
    }
}