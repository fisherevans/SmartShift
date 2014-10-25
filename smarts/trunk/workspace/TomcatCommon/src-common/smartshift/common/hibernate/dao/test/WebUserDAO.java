package smartshift.common.hibernate.dao.test;

import org.hibernate.Session;
import smartshift.common.hibernate.model.test.WebUser;

/**
 * Standard lookup functions for WebUsers
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class WebUserDAO {
    /**
     * @param username The username to lookup
     * @param session the DB session to use
     * @return The webuser with given username - null if none found
     */
    public static WebUser getWebUser(String username, Session session) {
        return (WebUser) session.get(WebUser.class, username);
    }
}
