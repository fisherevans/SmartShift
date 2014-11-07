package smartshift.accounts.jersey;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.dao.accounts.UserDAO;
import smartshift.common.hibernate.model.accounts.Business;
import smartshift.common.hibernate.model.accounts.ContactMethod;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.json.GsonFactory;
import smartshift.common.util.params.SimpleIntegerParam;

@Provider
@Path("/user")
public class UserActions {
    private static Logger logger = Logger.getLogger(UserActions.class);

    @Path("/self")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@Context HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return APIResultFactory.getOK(user.getGsonObject());
    }
    
    @Path("/business")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserBusinesses(@Context HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        List<Business.GsonObject> businesses = new ArrayList<>();
        for(Business business:user.getBusineses())
            businesses.add(business.getGsonObject());
        return APIResultFactory.getOK(businesses);
    }
    
    @Path("/business/{businessID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserBusiness(@Context HttpServletRequest request, @PathParam("businessID") SimpleIntegerParam businessID) {
        User user = (User) request.getAttribute("user");
        Business.GsonObject businessObj = null;
        for(Business business:user.getBusineses()) {
            if(business.getId().equals(businessID.getInteger())) {
                businessObj = business.getGsonObject();
                break;
            }
        }
        if(businessObj == null)
            return APIResultFactory.getResponse(Status.NOT_FOUND, "User is not linked to this business or this business does not exist.");
        return APIResultFactory.getOK(businessObj);
    }
    
    @Path("/contactMethod")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContactMethods(@Context HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return APIResultFactory.getOK(user.getUserContactMethodsGsonObjectMap());
    }
    
    @Path("/contactMethod/{contactMethodID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContactMethod(@Context HttpServletRequest request, @PathParam("contactMethodID") SimpleIntegerParam contactMethodID) {
        User user = (User) request.getAttribute("user");
        return APIResultFactory.getOK(user.getUserContactMethodsGsonObject(contactMethodID.getInteger()));
    }
}
