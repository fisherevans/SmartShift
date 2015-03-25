package smartshift.business.jersey.providers;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;
import smartshift.business.cache.bo.Cache;
import smartshift.business.cache.bo.Employee;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.security.session.UserSessionManager;
import smartshift.common.jersey.providers.AbstractAuthFilter;
import smartshift.common.security.session.UserSession;
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
        
        UserSession userSession = UserSessionManager.getSession(authString, true);
        if(userSession == null)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        logger.debug("processCredentials() Session found");
        
        if(!userSession.username.equals(username)) {
            logger.debug("processCredentials() Invalid username for session!");
            throw new WebApplicationException(getInvalidCredentialsResponse());
        }
        
        userSession.updateLastActivity();
        
        logger.debug("Session info - B:" + userSession.businessID + " E:" + userSession.employeeID);
        BusinessDAOContext daoContext = BusinessDAOContext.business(userSession.businessID);
        Cache cache = Cache.getCache(userSession.businessID);
        Employee employee = Employee.load(cache, userSession.employeeID);
        logger.debug("Employe = " + employee);
        
        containerRequest.setProperty("userSession", userSession);
        containerRequest.setProperty("daoContext", daoContext);
        containerRequest.setProperty("cache", cache);
        containerRequest.setProperty("employee", employee);
    }
}