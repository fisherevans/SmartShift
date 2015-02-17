package smartshift.business.jersey;

import smartshift.business.cache.bo.Cache;
import smartshift.common.jersey.ActionBase;
import smartshift.common.security.session.UserSession;

/** base action class for business actions
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public abstract class BusinessActionBase extends ActionBase {
    /**
     * @return the session used for auth
     */
    public UserSession getUserSession() {
        return (UserSession) getRequest().getAttribute("userSession");
    }
    
    /** gets the busines cache corresponding to the business id of the session
     * @return the business db session
     */
    public Cache getBusinessCache() {
        UserSession session = getUserSession();
        if(session == null)
            return null;
        return Cache.getCache(getUserSession().businessID);
    }
}
