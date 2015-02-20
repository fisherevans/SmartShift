package smartshift.accounts.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
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
     * Gets the user business employee relation for a user
     * @param userID the user to lookup
     * @param businessID the businessid
     * @param employeeID the employee id
     * @return the ube object. null if not found
     */
    public UserBusinessEmployeeModel uniqueByUserBusinessEmployee(Integer userID, Integer businessID, Integer employeeID) {
        UserBusinessEmployeeModel model = uniqueByCriteria(
                Restrictions.eq("userID", userID),
                Restrictions.eq("businessID", businessID),
                Restrictions.eq("employeeID", employeeID));
        return model;
    }
    
    /**
     * Gets the user business employee relations for a user and business
     * @param userID the user to lookup
     * @param businessID the businessid
     * @return the ube objects. null if not found
     */
    public ROCollection<UserBusinessEmployeeModel> listByUserBusiness(Integer userID, Integer businessID) {
        ROCollection<UserBusinessEmployeeModel> models = list(
                Restrictions.eq("userID", userID),
                Restrictions.eq("businessID", businessID));
        return models;
    }
    
    /**
     * Gets the user business employee relations for a user
     * @param userID the user to lookup
     * @return the ube objects. null if not found
     */
    public ROCollection<UserBusinessEmployeeModel> listByUser(Integer userID) {
        ROCollection<UserBusinessEmployeeModel> models = list(
                Restrictions.eq("userID", userID));
        return models;
    }
    
    /** Adds a ube relationship
     * @param userID the user id
     * @param businessID the business id
     * @param employeeID the employee id
     * @return the ube object, null on error
     * @throws DBException 
     */
    public UserBusinessEmployeeModel add(Integer userID, Integer businessID, Integer employeeID) throws DBException {
        UserBusinessEmployeeModel model = new UserBusinessEmployeeModel();
        model.setUserID(userID);
        model.setBusinessID(businessID);
        model.setEmployeeID(employeeID);
        model = add(model);
        return model;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
