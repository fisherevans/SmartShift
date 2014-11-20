package smartshift.common.hibernate.dao.accounts;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import com.google.gson.annotations.Expose;
import smartshift.common.hibernate.DBException;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * The data access object for the User Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserDAO extends BaseAccountsDAO {
    /**
     * Logger for this DAO
     */
    private static Logger logger = Logger.getLogger(UserDAO.class);
    
    /**
     * Fetch a user by username
     * @param username the username to lookup
     * @return the user - null if not found
     */
    public static User getUserByUsername(String username) {
        logger.debug("UserDAO.getUserByUsername() Enter - " + username);
        User user = GenericHibernateUtil.uniqueByCriterea(getAccountsSession(), User.class, Restrictions.eq("username", username));
        logger.debug("UserDAO.getUserByUsername() Got " + (user == null ? "null" : user.getUsername()));
        return user;
    }
    
    /**
     * Fetch a user by email
     * @param email the email to lookup
     * @return the user - null if not found
     */
    public static User getUserByEmail(String email) {
        logger.debug("UserDAO.getUserByEmail() Enter - " + email);
        User user = GenericHibernateUtil.uniqueByCriterea(getAccountsSession(), User.class, Restrictions.eq("email", email));
        logger.debug("UserDAO.getUserByEmail() Got " + (user == null ? "null" : user.getUsername()));
        return user;
    }
    
    /**
     * Fetch a user by id
     * @param id the id to lookup
     * @return the user - null if not found
     */
    public static User getUserById(Integer id) {
        logger.debug("UserDAO.getUserById() Enter - " + id);
        User user = GenericHibernateUtil.unique(getAccountsSession(), User.class, id);
        logger.debug("UserDAO.getUserById() Got " + (user == null ? "null" : user.getUsername()));
        return user;
    }
    
    /**
     * Get all users
     * @return the list of users
     */
    public static List<User> getUsers() {
        logger.debug("UserDAO.getUsers() Enter");
        List<User> users = GenericHibernateUtil.list(getAccountsSession(), User.class);
        logger.debug("UserDAO.getUsers() Got user count: " + users.size());
        return users;
    }
    
    /**
     * Add a user
     * @param request the add user requesy
     * @return the created user
     * @throws DBException if there was an error creating the user
     */
    public static User addUser(AddRequest request) throws DBException {
        logger.debug("UserDAO.addUser() Enter");
        User user = new User();
        user.setUsername(request.username);
        user.setEmail(request.email);
        user.setPassHash(BCrypt.hashpw(request.password, BCrypt.gensalt()));
        GenericHibernateUtil.save(getAccountsSession(), user);
        logger.debug("UserDAO.addUser() Success");
        return user;
    }

    /**
     * The object used to create a user
     * @author D. Fisher Evans <contact@fisherevans.com>
     *
     */
    public static class AddRequest {
        /**
         * The new unique username
         */
        @Expose
        String username;
        
        /**
         * The new unique email
         */
        @Expose
        String email;
        
        /**
         * The new password in plain text
         */
        @Expose
        String password;
    }
}