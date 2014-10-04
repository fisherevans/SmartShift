package smartshift.common.hibernate.dao.test;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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
        Criteria userCr = session.createCriteria(WebUser.class);
        userCr.add(Restrictions.eq("username", username));
        return (WebUser) userCr.uniqueResult();
    }
}
