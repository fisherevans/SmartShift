package smartshift.common.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import smartshift.common.hibernate.model.accounts.UserModel;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A "hello, world!" style landing page
 */
@Path("/hello")
public class Hello extends ActionBase {
    private static final Logger logger = Logger.getLogger(Hello.class);
    
    /**
     * @return a "Hello, world!" string
     */
	@GET
	public Response sayHello() {
        logger.debug("Hello.sayHello()");
	    UserModel user = getRequestUser();
	    String contextPath = getContext().getContextPath();
	    String responseText = "Hello, " + (user == null ? "Anon" : user.getUsername()) + "!";
	    responseText += " Welcome to " + contextPath + ".";
		return getMessageResponse(Status.OK, responseText);
	}
}