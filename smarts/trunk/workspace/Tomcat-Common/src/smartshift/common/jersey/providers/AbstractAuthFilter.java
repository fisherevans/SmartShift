package smartshift.common.jersey.providers;

import java.io.IOException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import smartshift.common.security.BasicAuth;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.log4j.SmartLogger;

/** The base for authentications
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public abstract class AbstractAuthFilter implements ContainerRequestFilter {
    private static final SmartLogger logger = new SmartLogger(AbstractAuthFilter.class);
    
    /**
     * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws IOException {
        logger.debug("filter() Enter");
        // http://stackoverflow.com/questions/18499465/cors-and-http-basic-auth
        // http://stackoverflow.com/questions/19234892/xmlhttprequest-based-cors-call-with-basic-auth-fails-in-firefox-and-chrome
        // Ignore Options Requests - preflight browsers
        if(containerRequest.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS))
            return;
        logger.debug("filter() Not a OPTIONS request");
        String auth = containerRequest.getHeaders().getFirst("Authorization");
        if(auth == null)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        logger.debug("filter() Auth header found");
        String[] authData = BasicAuth.decode(auth);
        if(authData == null || authData.length != 2)
            throw new WebApplicationException(getInvalidCredentialsResponse());
        logger.debug("filter() valid header value");
        String username = authData[0];
        String authString = authData[1];
        logger.info(String.format("Processing: %s %s (%s)", containerRequest.getMethod(), containerRequest.getUriInfo().getPath(), username));
        processCredentials(containerRequest, username, authString);
    }

    protected abstract void processCredentials(ContainerRequestContext containerRequest, String username, String authString);

    /**
     * Gets a premade response for invalid credentials
     * @return the response
     */
    public static Response getInvalidCredentialsResponse() {
        logger.debug("AuthFilter.getInvalidCredentialsResponse() Enter");
        return APIResultFactory.getResponse(Status.UNAUTHORIZED, null, "Invalid credentials!");
    }
}
