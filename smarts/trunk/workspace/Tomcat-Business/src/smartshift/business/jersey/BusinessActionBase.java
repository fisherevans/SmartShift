package smartshift.business.jersey;

import smartshift.business.cache.bo.Cache;
import smartshift.common.jersey.ActionBase;
import smartshift.common.security.session.UserSession;

/** base action class for business actions
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class BusinessActionBase extends ActionBase {
    /**
     * @return the session used for auth
     */
    public UserSession getUserSession() {
        return (UserSession) getContext().getAttribute("userSession");
    }
    
    public Cache getBusinessCache() {
        return Cache.getCache(getUserSession().businesID);
    }
}
