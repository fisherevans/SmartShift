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
@Path("/employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeActions extends BusinessActionBase {
    private static final SmartLogger logger = new SmartLogger(BusinessActionBase.class);
    
    @GET
    @Path("/{id}")
    public Response simple(@PathParam("id") SimpleIntegerParam employeeID) {
        Employee employee = Employee.load(getBusinessCache(), employeeID.getInteger());
        if(employee == null) {
            logger.warn("Failed to find employee: " + getUserSession().employeeID);
            return getMessageResponse(Status.INTERNAL_SERVER_ERROR, "Something went wrong...");
        }
        return getObjectResponse(Status.OK, EmployeeJSON.getSimple(employee));
    }
    
    @GET
    @Path("/full/{id}")
    public Response full(@PathParam("id") SimpleIntegerParam employeeID) {
    	Employee employeeSelf = Employee.load(getBusinessCache(), getUserSession().employeeID);
        if(employeeSelf == null) {
            logger.warn("Failed to find employee: " + getUserSession().employeeID);
            return getMessageResponse(Status.INTERNAL_SERVER_ERROR, "Something went wrong...");
        }
        Employee employeeLookup = Employee.load(getBusinessCache(), employeeID.getInteger());
        if(employeeLookup == null)
    		return getMessageResponse(Status.BAD_REQUEST, "This employee does not exist.");
    	if(employeeSelf.getID() == employeeLookup.getID() || employeeSelf.manages(employeeLookup))
            return getObjectResponse(Status.OK, EmployeeJSON.getFull(employeeLookup));
    	else
    		return getMessageResponse(Status.BAD_REQUEST, "You can not manage this emplpoyee.");
    }
}
