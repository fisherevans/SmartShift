package smartshift.common.jersey.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import smartshift.common.util.properties.AppProperties;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        String requestOrigin = requestContext.getHeaders().getFirst("origin");
        if(requestOrigin != null) {
            headers.add("Access-Control-Allow-Origin", requestOrigin);
        }
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Credentials", "true");
        String requestHead = requestContext.getHeaders().getFirst("Access-Control-Request-Headers");
        if(requestHead != null && requestHead.length() != 0) {
            headers.add("Access-Control-Allow-Headers", requestHead);
        }
    }
}
