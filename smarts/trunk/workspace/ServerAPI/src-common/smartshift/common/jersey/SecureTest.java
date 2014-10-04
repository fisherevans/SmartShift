package smartshift.common.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

public class SecureTest {
    @Path("/admin")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String get(@Context SecurityContext sc) {
        if(sc.isUserInRole("PreferredCustomer")) {
            return "Secured!";
        } else {
            return "Get off my lawn!";
        }
    }
}
