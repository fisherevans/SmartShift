package smartshift.business.jersey.providers;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;
import smartshift.business.hibernate.dao.BusinessDAOContext;
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
        logger.debug("Processing auth for " + username + ":" + authString.charAt(0) + "..." + authString.charAt(authString.length()-1));
        UserSession session = UserSessionManager.getSession(authString, true);
        if(session == null)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        BusinessDAOContext daoContext = BusinessDAOContext.business(session.businessID);
        logger.debug("filter() Session found");
        containerRequest.setProperty("userSession", session);
        containerRequest.setProperty("daoContext", daoContext);
    }
}