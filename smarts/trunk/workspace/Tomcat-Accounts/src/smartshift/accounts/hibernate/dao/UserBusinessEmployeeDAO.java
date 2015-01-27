package smartshift.accounts.hibernate.dao;

import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.accounts.hibernate.model.UserModel;

/**
 * The data access object for the User Business Employee Relationship
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserBusinessEmployeeDAO extends BaseAccountsDAO {
    
    /**
     * Gets the user business employee relation for a user
     * @param user the user to lookup
     * @param businessID the businessid
     * @param employeeID the employee id
     * @return the ube object. null if not found
     */
    public static UserBusinessEmployeeModel getUBE(UserModel user, Integer businessID, Integer employeeID) {
        for(UserBusinessEmployeeModel ube:user.getUserBusinessEmployees()) {
            BusinessModel b = ube.getBusiness();
            Integer e = ube.getEmployeeID();
            if(b != null && businessID.equals(b.getId()) && e != null && employeeID.equals(e))
                return ube;
        }
        return null; // else
    }
}
