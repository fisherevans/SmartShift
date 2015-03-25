package smartshift.business.jersey;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import smartshift.business.cache.bo.Cache;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.updates.BaseUpdate;
import smartshift.business.updates.UpdateManager;
import smartshift.common.jersey.BaseActions;
import smartshift.common.security.session.UserSession;
import smartshift.common.util.log4j.SmartLogger;

/** base action class for business actionsR
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public abstract class BaseBusinessActions extends BaseActions {
	private static final SmartLogger logger = new SmartLogger(BaseBusinessActions.class);
	
    private UserSession _userSession = null;
    
    private Cache _cache = null;
    
    private Employee _requestEmployee = null;
    
    private BusinessDAOContext _daoContext = null;
    
    /**
     * @return the session used for auth
     */
    public UserSession getUserSession() {
        if(_userSession == null)
            _userSession = (UserSession) getRequest().getAttribute("userSession");
        return _userSession;
    }
    
    /**
     * @return the cache for the business of this request
     */
    public Cache getCache() {
        if(_cache == null)
            _cache = (Cache) getRequest().getAttribute("cache");
        return _cache;
    }
    
    /**
     * @return the employee of this request
     */
    public Employee getRequestEmployee() {
        if(_requestEmployee == null)
            _requestEmployee = (Employee) getRequest().getAttribute("employee");
        return _requestEmployee;
    }
    
    /**
     * @return gets the Business DAO context tied to this request
     */
    public BusinessDAOContext getDAOContext() {
        if(_daoContext == null)
            _daoContext = (BusinessDAOContext) getRequest().getAttribute("daoContext");
        return _daoContext;
    }
    
    /**
     * Gets a Group BO
     * @param groupID the group id
     * @param requireManages set true if should return null if group is not managed by employee
     * @return the group. null if invalid id. null if requireManages is true and employee does not manage it
     */
    public Group getGroup(Integer groupID, boolean requireManages) {
        // TODO only check for master manager
        logger.debug("getGroup() Enter");
        Group group = Group.load(getCache(), groupID);
        if(group == null) 
            throw new WebApplicationException(BaseActions.getMessageResponse(Status.BAD_REQUEST, "The group does not exist: " + groupID));
        else if(requireManages && !getRequestEmployee().manages(group, true))
            throw new WebApplicationException(BaseActions.getMessageResponse(Status.BAD_REQUEST, "You do not manage this group: " + groupID));
        logger.debug("getGroup() Valid group found");
        return group;
    }
    
    /**
     * @param roleID the role id to look up
     * @return the role object
     */
    public Role getRole(Integer roleID) {
        logger.debug("getRole() Enter: " + roleID);
        Role role = Role.load(getCache(), roleID);
        if(role == null) {
            String error = "The role does not exist: " + roleID;
            throw new WebApplicationException(BaseActions.getMessageResponse(Status.BAD_REQUEST, error));
        }
        logger.debug("getRole() Valid role found");
        return role;
    }
    
    /**
     * @param employeeID the employee id to look up
     * @param requireManages true if the request user must be a manager for the employee
     * @return the employee object
     */
    public Employee getEmployee(Integer employeeID, boolean requireManages) {
        logger.debug("getRole() Enter: " + employeeID);
        Employee employee = Employee.load(getCache(), employeeID);
        if(employee == null) 
            throw new WebApplicationException(BaseActions.getMessageResponse(Status.BAD_REQUEST, "The employee does not exist: " + employeeID));
        else if(requireManages && !getRequestEmployee().manages(employee))
            throw new WebApplicationException(BaseActions.getMessageResponse(Status.BAD_REQUEST, "You do not manage this employee: " + employeeID));
        logger.debug("getRole() Valid employee found");
        return employee;
    }
    
    public UpdateManager getUpdateManager() {
        return UpdateManager.getManager(getUserSession().businessID);
    }
    
    public void addUpdate(BaseUpdate update) {
        update.setExecuter(getRequestEmployee());
        getUpdateManager().addUpdate(update, getUserSession().sessionID);
    }
}
