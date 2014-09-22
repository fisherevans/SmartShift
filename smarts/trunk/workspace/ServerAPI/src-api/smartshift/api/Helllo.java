package smartshift.api;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hibernate.Query;
import org.hibernate.Session;

@Path("/hello")
public class Helllo {
    @Context
    private ServletContext context;
    
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "Welcome to: " + context.getContextPath();
	}
}