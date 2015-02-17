package smartshift.business.jersey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.jersey.objects.RoleJSON;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.params.SimpleIntegerParam;

@Provider
@Path("/role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleActions extends BusinessActionBase {
    private static final SmartLogger logger = new SmartLogger(BusinessActionBase.class);
    
    @GET
    @Path("/{ids}")
    public Response getRoles(@PathParam("ids") String roleIDs) {
    	Set<Role> roles = new HashSet<Role>();
    	StringBuffer error = new StringBuffer();
    	String[] ids = roleIDs.split("-");
    	for(String id:ids) {
    		try {
    			Integer intId = new Integer(id);
    			Role role = Role.load(getBusinessCache(), intId);
    			if(role == null)
    				error.append(id + " is an invalid role id. ");
    			else
    				roles.add(role);
    		} catch(Exception e) {
    			error.append(id + " is an invalid integer. ");
    		}
    	}
    	if(error.length() > 0)
    		return getMessageResponse(Status.BAD_REQUEST, error.toString());
    	Map<Integer, RoleJSON> roleJsons = new HashMap<>();
    	for(Role role:roles)
    		roleJsons.put(role.getID(), new RoleJSON(role));
        return getObjectResponse(Status.OK, roleJsons);
    }
}
