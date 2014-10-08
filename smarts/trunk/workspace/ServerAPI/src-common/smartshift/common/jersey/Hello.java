package smartshift.common.jersey;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A "hello, world!" style landing page
 */
@Path("/hello")
public class Hello {

    private static final Logger logger = Logger.getLogger(Hello.class);

    @Context
    private ServletContext context;
    
    /**
     * @return a "Hello, world!" string
     */
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String sayHtmlHello() {
		return "Welcome to: " + context.getContextPath();
	}
}