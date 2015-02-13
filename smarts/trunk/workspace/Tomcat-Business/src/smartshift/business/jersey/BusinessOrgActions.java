package smartshift.business.jersey;

import java.util.HashMap;
import java.util.HashSet;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import org.hibernate.mapping.Set;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.hibernate.dao.GroupDAO;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.jersey.objects.RoleJSON;
import smartshift.common.util.collections.ROList;
import smartshift.common.util.log4j.SmartLogger;

@Provider
@Path("/org")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BusinessOrgActions extends BusinessActionBase {
    private static final SmartLogger logger = new SmartLogger(BusinessActionBase.class);
    @GET
    @Path("/employee/self")
    public Response userSelf() {
        Employee employee = Employee.load(getBusinessCache(), getUserSession().employeeID);
        if(employee == null) {
            logger.warn("Failed to find employee: " + getUserSession().employeeID);
            return getMessageResponse(Status.INTERNAL_SERVER_ERROR, "Something went wrong...");
        }
        EmployeeJSON employeeJson = new EmployeeJSON(employee);
        employeeJson.groupRoles = new HashMap<>();
        ROList<GroupModel> groups = getBusinessCache().getDAOContext().dao(GroupDAO.class).getEmployeeGroups(employee.getID());
        for(GroupModel group:groups) {
            GroupJSON groupJson = new GroupJSON(group);
            employeeJson.groupRoles.put(groupJson, new HashSet<RoleJSON>());
        }
        return getObjectResponse(Status.OK, employeeJson);
    }
}
