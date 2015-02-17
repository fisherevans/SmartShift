package smartshift.common.jersey.providers.exceptionMappers;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * maps the jersey exception to to match the style of the smartshift api
 */
@Provider
public class ServerErrorExceptionMapper implements ExceptionMapper<ServerErrorException> {
    private static final SmartLogger logger = new SmartLogger(ServerErrorExceptionMapper.class);

    /**
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(ServerErrorException exception) {
        logger.warn("Uncaught exception caught by mapper.", exception);
        return APIResultFactory.getResponse(Status.INTERNAL_SERVER_ERROR, null, "An internal error has occurred.");
    }
}
