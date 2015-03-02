package smartshift.business.jersey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
import smartshift.business.jersey.objects.GroupRequestJSON;
import smartshift.business.jersey.objects.GroupRoleEmployeeRequestJSON;
import smartshift.business.jersey.objects.GroupRoleRequestJSON;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.util.GroupRoleEmployeeUtil;
import smartshift.common.util.ValidationUtil;
import smartshift.common.util.log4j.SmartLogger;

/** @author D. Fisher Evans <contact@fisherevans.com> jersey group actions 
 * jersey actions for groups
 */
@Provider
@Path("/group")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);

    /** get a map of groupid -> group objects
     * @param groupIDs the dahs eperated list of grp ids
     * @return the group map
     */
    @GET
    @Path("/{ids}")
    public Response getGroups(@PathParam("ids") String groupIDs) {
        logger.debug("Entering getGroups()");
        Set<Group> groups = new HashSet<Group>();
        StringBuffer error = new StringBuffer();
        String[] ids = groupIDs.split("-");
        for(String id : ids) {
            try {
                Integer intId = new Integer(id);
                Group group = Group.load(getCache(), intId);
                if(group == null)
                    error.append(id + " is an invalid group id. ");
                else
                    groups.add(group);
            } catch(Exception e) {
                error.append(id + " is an invalid integer. ");
            }
        }
        if(error.length() > 0) {
            logger.debug("Errors found in getGroups");
            return getMessageResponse(Status.BAD_REQUEST, error.toString());
        }
        Map<Integer, GroupJSON> groupJsons = new HashMap<>();
        for(Group group : groups)
            groupJsons.put(group.getID(), new GroupJSON(group));
        logger.debug("Returning " + groupJsons.size() + " groups");
        return getObjectResponse(Status.OK, groupJsons);
    }
    
    @PUT
    public Response addGroup(GroupRequestJSON request) {
        String name = ValidationUtil.validateName(request.getName());
        if(name == null)
            return getMessageResponse(Status.BAD_REQUEST, "Groups must have a valid name.");
        Group parent = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.getParentGroupID(), true);
        if(parent == null)
            return getMessageResponse(Status.BAD_REQUEST, "Groups must have a valid parent group that you manage.");
        Group group = Group.create(getCache().getBusinessID(), name, parent);
        return getObjectResponse(Status.OK, new GroupJSON(group));
    }
    
    @PUT
    @Path("/employee")
    public Response addGroupEmployees(GroupEmployeeRequestJSON request) {
        // TODO - error if employee already exists in group?
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.getGroup(), true);
        if(group == null)
            return getMessageResponse(Status.BAD_REQUEST, "You must manage the group to add employees to it.");
        if(request.getEmployees() == null || request.getEmployees().size() == 0)
            return getMessageResponse(Status.BAD_REQUEST, "You must pass employees to add.");
        List<Employee> employees = new ArrayList<>();
        for(Integer employeeID:request.getEmployees()) {
            Employee employee = Employee.load(getCache(), employeeID);
            if(employee == null || !getEmployee().manages(employee))
                return getMessageResponse(Status.BAD_REQUEST, "You  do not manage the employee: " + employeeID);
            employees.add(employee);
        }
        for(Employee employee:employees)
            group.addEmployee(employee, Role.getBasicRole(getCache(), group));
        return getMessageResponse(Status.OK, "All employees were added to the group.");
    }

    @PUT
    @Path("/role")
    public Response addGroupRoles(GroupRoleRequestJSON request) {
        // TODO - error if role already exists in group?
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.getGroupID(), true);
        if(group == null)
            return getMessageResponse(Status.BAD_REQUEST, "You must manage the group to add roles to it.");
        if(request.getRoleNames() == null || request.getRoleNames().size() == 0)
            return getMessageResponse(Status.BAD_REQUEST, "You must pass roles to add.");
        // TODO - need to lookup role's in case one with a name already exists
        for(String roleName:request.getRoleNames()) {
            Role role = Role.create(getCache().getBusinessID(), roleName, group);
            group.addRole(role);
        }
        return getObjectResponse(Status.OK, new GroupJSON(group));
    }
    
    @PUT
    @Path("/role/employee")
    public Response addGroupRoleEmployees(GroupRoleEmployeeRequestJSON request) {
        // TODO - error if employee already exists in group role?
        Group group = GroupRoleEmployeeUtil.getGroup(getCache(), getEmployee(), request.getGroupID(), true);
        if(group == null)
            return getMessageResponse(Status.BAD_REQUEST, "You must manage the group to add employees to it.");
        Role role = Role.load(getCache(), request.getRoleID());
        if(role == null || !group.hasRole(role))
            return getMessageResponse(Status.BAD_REQUEST, "The group must have this role to add employees to it.");
        if(request.getEmployeeIDs() == null || request.getEmployeeIDs().size() == 0)
            return getMessageResponse(Status.BAD_REQUEST, "You must pass employees to add.");
        List<Employee> employees = new ArrayList<>();
        for(Integer employeeID:request.getEmployeeIDs()) {
            Employee employee = Employee.load(getCache(), employeeID);
            if(employee == null || !getEmployee().manages(employee))
                return getMessageResponse(Status.BAD_REQUEST, "You  do not manage the employee: " + employeeID);
            employees.add(employee);
        }
        for(Employee employee:employees)
            group.addEmployee(employee, role);
        return getMessageResponse(Status.OK, "All employees were added to the group role.");
    }
}
