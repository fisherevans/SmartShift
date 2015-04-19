package smartshift.accounts.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.common.hibernate.dao.tasks.criteria.ListByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.criteria.UniqueByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the User Business Employee Relationship
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserBusinessEmployeeDAO extends BaseAccountsDAO<UserBusinessEmployeeModel> {
    private static final SmartLogger logger = new SmartLogger(UserBusinessEmployeeDAO.class);

    /**
     * Initializes the object.
     */
    public UserBusinessEmployeeDAO() {
        super(UserBusinessEmployeeModel.class);
    }

    /**
     * Gets a task that gets the user business employee relation for a user
     * @param userID the user to lookup
     * @param businessID the business id
     * @param employeeID the employee id
     * @return the task object
     */
    public UniqueByCriteriaTask<UserBusinessEmployeeModel> uniqueByUserBusinessEmployee(Integer userID, Integer businessID, Integer employeeID) {
        return uniqueByCriteria(
                Restrictions.eq("userID", userID),
                Restrictions.eq("businessID", businessID),
                Restrictions.eq("employeeID", employeeID));
    }
    
    /**
     * Gets a task that gets the user business employee relations for a user and business
     * @param userID the user to lookup
     * @param businessID the business id
     * @return the task object
     */
    public ListByCriteriaTask<UserBusinessEmployeeModel> listByUserBusiness(Integer userID, Integer businessID) {
        return list(
                Restrictions.eq("userID", userID),
                Restrictions.eq("businessID", businessID));
    }
    
    /**
     * Gets a task that gets the user business employee relations for a user
     * @param userID the user to lookup
     * @return the task object
     */
    public ListByCriteriaTask<UserBusinessEmployeeModel> listByUser(Integer userID) {
        return list(Restrictions.eq("userID", userID));
    }
    
    /** Gets a task that adds a ube relationship
     * @param userID the user id
     * @param businessID the business id
     * @param employeeID the employee id
     * @return the task object 
     */
    public AddTask<UserBusinessEmployeeModel> add(Integer userID, Integer businessID, Integer employeeID) {
        UserBusinessEmployeeModel model = new UserBusinessEmployeeModel();
        model.setUserID(userID);
        model.setBusinessID(businessID);
        model.setEmployeeID(employeeID);
        return add(model);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
