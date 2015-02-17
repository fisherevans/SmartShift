package smartshift.common.jersey.providers.exceptionMappers;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import smartshift.common.util.json.APIResultFactory;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * maps the jersey exception to to match the style of the smartshift api
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    /**
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(NotFoundException exception) {
        return APIResultFactory.getResponse(Status.NOT_FOUND, null, "Method not found.");
    }
}
