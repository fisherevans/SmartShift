package smartshift.common.hibernate.dao.accounts;

import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.json.APIResultFactory;

/**
 * Standard lookup functions for Users
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserDAO {
    private static Logger logger = Logger.getLogger(UserDAO.class);
    
    public static User getUserByUsername(String username) {
        Session session = HibernateFactory.getSession(HibernateFactory.ACCOUNTS);
        User user = GenericHibernateUtil.uniqueByCriterea(session, User.class, Restrictions.eq("username", username));
        return user;
    }
    
    public static User getUserByEmail(String email) {
        Session session = HibernateFactory.getSession(HibernateFactory.ACCOUNTS);
        User user = GenericHibernateUtil.uniqueByCriterea(session, User.class, Restrictions.eq("email", email));
        return user;
    }
    
    public static User getUserById(Integer id) {
        Session session = HibernateFactory.getSession(HibernateFactory.ACCOUNTS);
        User user = GenericHibernateUtil.unique(session, User.class, id);
        return user;
    }
    
    public static List<User> getUsers() {
        Session session = HibernateFactory.getSession(HibernateFactory.ACCOUNTS);
        List<User> users = GenericHibernateUtil.list(session, User.class);
        return users;
    }
    
    public static User addUser(User.AddRequest addRequest) {
        Session session = HibernateFactory.getSession(HibernateFactory.ACCOUNTS);
        User user = new User(addRequest);
        GenericHibernateUtil.save(session, user);
        return user;
    }
}