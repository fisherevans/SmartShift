package smartshift.notifications;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class NotificationDaemon {
    @Context
    private ServletContext context;

    /**
     * @return a "Hello, world!" string
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "Welcome to: " + context.getContextPath();
    }
}
