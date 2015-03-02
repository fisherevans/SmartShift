package smartshift.accounts.jersey;

import smartshift.accounts.cache.bo.User;
import smartshift.common.jersey.BaseActions;

/**
 * The base action object for accounts services
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public abstract class BaseAccountActions extends BaseActions {
    private User user = null;
    
    /**
     * @return The User from the Request
     */
    protected User getRequestUser() {
        if(user == null)
            user = (User) getRequest().getAttribute("user");
        return user;
    }
}
