package smartshift.common.jersey.providers;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.log4j.SmartLogger;

@Provider
public class SmartExceptionMapper implements ExceptionMapper {
    private static final SmartLogger logger = new SmartLogger(SmartExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        if(exception instanceof NotFoundException) {
            return APIResultFactory.getResponse(Status.NOT_FOUND, null, "Method not found.");
        } else { // ServerErrorException and everything else...
            logger.warn("Got uncaught excetion!", exception);
            return APIResultFactory.getResponse(Status.NOT_FOUND, null, "An internal server error has occurred.");
        }
    }

}
