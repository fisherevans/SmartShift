package smartshift.accounts.jersey;

import smartshift.accounts.cache.bo.User;
import smartshift.common.jersey.ActionBase;

/**
 * The base action object for accounts services
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class AccountsActionBase extends ActionBase {
    /**
     * @return The User from the Request
     */
    protected User getRequestUser() {
        return (User) getRequest().getAttribute("user");
    }
}
