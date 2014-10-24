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
    private static String ALLOW_ORIGIN = null;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if(ALLOW_ORIGIN == null)
            ALLOW_ORIGIN = AppProperties.getProperty("cors.allowOrigin", "localhost");
          
          MultivaluedMap<String, Object> headers = responseContext.getHeaders();
          headers.add("Access-Control-Allow-Origin", "ALLOW_ORIGIN");
          headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
          String requestHead = requestContext.getHeaderString("Access-Control-Request-Headers");
          if(requestHead != null && requestHead.length() != 0) {
            headers.add("Access-Control-Allow-Headers", requestHead);
          }
    }
}
