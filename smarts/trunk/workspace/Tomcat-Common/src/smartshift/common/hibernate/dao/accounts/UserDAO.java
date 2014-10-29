package smartshift.common.hibernate.dao.accounts;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import smartshift.common.hibernate.model.accounts.User;

/**
 * Standard lookup functions for Users
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserDAO {
    /**
     * @param username The username to lookup
     * @param session the DB session to use
     * @return The user with given username - null if none found
     */
    public static User getUser(Session session, String username) {
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("username", username));
        return (User) crit.uniqueResult();
    }
}