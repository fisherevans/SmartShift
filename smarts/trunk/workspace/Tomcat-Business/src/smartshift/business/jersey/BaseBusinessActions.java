package smartshift.business.jersey;

import smartshift.business.cache.bo.Cache;
import smartshift.business.cache.bo.Employee;
import smartshift.business.hibernate.dao.BusinessDAOContext;
import smartshift.common.jersey.BaseActions;
import smartshift.common.security.session.UserSession;

/** base action class for business actionsR
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public abstract class BaseBusinessActions extends BaseActions {
    private UserSession userSession = null;
    
    private Cache cache = null;
    
    private Employee employee = null;
    
    private BusinessDAOContext daoContext = null;
    
    /**
     * @return the session used for auth
     */
    public UserSession getUserSession() {
        if(userSession == null)
            userSession = (UserSession) getRequest().getAttribute("userSession");
        return userSession;
    }
    
    /**
     * @return the cache for the business of this request
     */
    public Cache getCache() {
        if(cache == null)
            cache = (Cache) getRequest().getAttribute("cache");
        return cache;
    }
    
    /**
     * @return the employee of this request
     */
    public Employee getEmployee() {
        if(employee == null)
            employee = (Employee) getRequest().getAttribute("employee");
        return employee;
    }
    
    /**
     * @return gets the Business DAO context tied to this request
     */
    public BusinessDAOContext getDAOContext() {
        if(daoContext == null)
            daoContext = (BusinessDAOContext) getRequest().getAttribute("daoContext");
        return daoContext;
    }
}
