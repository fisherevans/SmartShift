package smartshift.business.jersey;

import smartshift.business.cache.bo.Cache;
import smartshift.business.hibernate.dao.BusinessDAOContext;
import smartshift.common.jersey.BaseActions;
import smartshift.common.security.session.UserSession;

/** base action class for business actionsR
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public abstract class BaseBusinessActions extends BaseActions {
    /**
     * @return the session used for auth
     */
    public UserSession getUserSession() {
        return (UserSession) getRequest().getAttribute("userSession");
    }
    
    /**
     * @return the cache for the business of this request
     */
    public Cache getCache() {
        return (Cache) getRequest().getAttribute("cache");
    }
    
    /**
     * @return gets the Business DAO context tied to this request
     */
    public BusinessDAOContext getDAOContext() {
        return (BusinessDAOContext) getRequest().getAttribute("daoContext");
    }
}
