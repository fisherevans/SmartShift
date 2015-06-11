package smartshift.accounts.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.common.hibernate.dao.tasks.criteria.UniqueByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the UserModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserDAO extends BaseAccountsDAO<UserModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(UserDAO.class);

    /**
     * Initializes the object.
     */
    public UserDAO() {
        super(UserModel.class);
    }
    
    /**
     * Gets a task that fetch a UserModel by Username
     * @param username the username to lookup
     * @return the task object
     */
    public UniqueByCriteriaTask<UserModel> uniqueByUsername(String username) {
        return uniqueByCriteria(Restrictions.eq("username", username));
    }
    
    /**
     * Gets a task that fetch a UserModel by email
     * @param email the email to lookup
     * @return the task object
     */
    public UniqueByCriteriaTask<UserModel> uniqueByEmail(String email) {
        return uniqueByCriteria(Restrictions.eq("email", email));
    }
    
    /**
     * Gets a task that adds a UserModel
     * @param username 
     * @param email 
     * @param password 
     * @return the task object
     */
    public AddTask<UserModel> add(String username, String email, String password) {
        UserModel model = new UserModel();
        model.setUsername(username);
        model.setEmail(email);
        model.setPassHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        return add(model);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}