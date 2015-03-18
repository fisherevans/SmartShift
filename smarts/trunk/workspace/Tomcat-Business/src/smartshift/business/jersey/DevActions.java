package smartshift.business.jersey;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.cache.bo.CachedObject;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.jersey.objects.FullCacheJSON;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.jersey.objects.RoleJSON;
import smartshift.common.util.UID;
import smartshift.common.util.collections.ROMap;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 *  Temporary actions used for development
 */
@Provider
@Path("/dev")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DevActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(DevActions.class);
    
    /**
     * @return all groups, roles, employees and how they're linked
     */
    @GET
    @Path("/fullCache")
    public Response getFullCache() {
        FullCacheJSON json = new FullCacheJSON();
        json.selfEmployeeID = getEmployee().getID();
        json.employees = new HashMap<Integer, EmployeeJSON>();
        json.groups = new HashMap<Integer, GroupJSON>();
        json.roles = new HashMap<Integer, RoleJSON>();
        json.groupRoleEmployeeIDs = new HashMap<Integer, Map<Integer, Set<Integer>>>();
        
        ROMap<UID, WeakReference<CachedObject>> cache = getCache().getROCacheMap();
        
        for(UID uid:cache.keySet()) {
            logger.debug("Loading UID" + uid.toString());
            switch(uid.getType()) {
                case Employee.TYPE_IDENTIFIER: {
                    Employee employee = (Employee) cache.get(uid).get();
                    logger.debug("  Employee: " + (employee == null ? employee : "ID:" + employee.getID()));
                    json.employees.put(employee.getID(), new EmployeeJSON(employee));
                    break;
                }
                case Group.TYPE_IDENTIFIER: {
                    Group group = (Group) cache.get(uid).get();
                    logger.debug("  Group: " + (group == null ? group : "ID:" + group.getID()));
                    json.groups.put(group.getID(), new GroupJSON(group));
                    break;
                }
                case Role.TYPE_IDENTIFIER: {
                    Role role = (Role) cache.get(uid).get();
                    logger.debug("  Role: " + (role == null ? role : "ID:" + role.getID()));
                    json.roles.put(role.getID(), new RoleJSON(role));
                    break;
                }
            }
        }
        
        for(Integer groupID:json.groups.keySet()) {
            Group group = Group.load(getCache(), groupID);
            logger.debug("Getting GRE for Group: " + groupID + ":" + group);
            Map<Integer, Set<Integer>> roleEmployeeIDs = new HashMap<>();
            for(Role role:group.getRoles()) {
                logger.debug("  Role: " +  role.getID());
                Set<Integer> employeeIDs = new HashSet<>();
                for(Employee employee:group.getRoleEmployees(role)) {
                    logger.debug("    Employee: " +  (employee == null ? null : employee.getID()));
                    if(employee != null)
                        employeeIDs.add(employee.getID());
                }
                roleEmployeeIDs.put(role.getID(), employeeIDs);
            }
            json.groupRoleEmployeeIDs.put(groupID, roleEmployeeIDs);
        }
        
        return getObjectResponse(Status.OK, json);
    }
}
