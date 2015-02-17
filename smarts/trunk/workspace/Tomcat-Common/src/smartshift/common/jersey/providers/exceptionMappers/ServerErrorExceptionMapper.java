package smartshift.common.jersey.providers.exceptionMappers;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.log4j.SmartLogger;

@Provider
public class ServerErrorExceptionMapper implements ExceptionMapper<ServerErrorException> {
    private static final SmartLogger logger = new SmartLogger(ServerErrorExceptionMapper.class);

    @Override
    public Response toResponse(ServerErrorException exception) {
        logger.warn("Uncaught exception caught by mapper.", exception);
        return APIResultFactory.getResponse(Status.INTERNAL_SERVER_ERROR, null, "An internal error has occurred.");
    }
}
