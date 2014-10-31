package smartshift.api.jersey;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.dao.accounts.UserDAO;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.params.SimpleIntegerParam;

@Path("user")
public class UserActions {
    private static Logger logger = Logger.getLogger(UserActions.class);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User.AddRequest addRequest) {
        User user = UserDAO.addUser(addRequest);
        return APIResultFactory.getOK(user.getGsonObject());
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User.GsonObject> objects = new ArrayList<>();
        for(User user:UserDAO.getUsers())
            objects.add(user.getGsonObject());
        return APIResultFactory.getOK(objects);
    }
    
    @Path("username/{username}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUsername(@PathParam("username") String username) {
        User user = UserDAO.getUserByUsername(username);
        return APIResultFactory.getOK(user.getGsonObject());
    }
    
    @Path("email/{email}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("email") String email) {
        User user = UserDAO.getUserByEmail(email);
        return APIResultFactory.getOK(user.getGsonObject());
    }
    
    @Path("{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") SimpleIntegerParam id) {
        User user = UserDAO.getUserById(id.getInteger());
        return APIResultFactory.getOK(user.getGsonObject());
    }
}
