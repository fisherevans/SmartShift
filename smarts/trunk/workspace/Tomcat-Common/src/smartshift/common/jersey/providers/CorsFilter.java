package smartshift.common.jersey.providers;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    private static final Logger logger = Logger.getLogger(CorsFilter.class);
    
    /**
     * @see javax.ws.rs.container.ContainerResponseFilter#filter(javax.ws.rs.container.ContainerRequestContext, javax.ws.rs.container.ContainerResponseContext)
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        logger.debug("CorsFilter.filter()");
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        String requestOrigin = requestContext.getHeaders().getFirst("origin");
        if(requestOrigin != null) {
            headers.putSingle("Access-Control-Allow-Origin", requestOrigin);
        }
        headers.putSingle("Content-Type", MediaType.APPLICATION_JSON);
        headers.putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.putSingle("Access-Control-Allow-Credentials", "true");
        String requestHead = requestContext.getHeaders().getFirst("Access-Control-Request-Headers");
        if(requestHead != null && requestHead.length() != 0) {
            headers.putSingle("Access-Control-Allow-Headers", requestHead);
        }
    }
}
