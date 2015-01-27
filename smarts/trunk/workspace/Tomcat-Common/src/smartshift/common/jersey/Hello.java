package smartshift.common.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import smartshift.common.cache.bo.accounts.User;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A "hello, world!" style landing page
 */
@Path("/hello")
public class Hello extends ActionBase {
    private static final SmartLogger logger = new SmartLogger(Hello.class);
    
    /**
     * @return a "Hello, world!" string
     */
	@GET
	public Response sayHello() {
        logger.debug("Hello.sayHello()");
	    User user = getRequestUser();
	    String contextPath = getContext().getContextPath();
	    String responseText = "Hello, " + (user == null ? "Anon" : user.getUserName()) + "!";
	    responseText += " Welcome to " + contextPath + ".";
		return getMessageResponse(Status.OK, responseText);
	}
}