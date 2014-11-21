package smartshift.common.hibernate.dao.accounts;

import smartshift.common.hibernate.model.accounts.Business;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.hibernate.model.accounts.UserBusinessEmployee;

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
    public static UserBusinessEmployee getUBE(User user, Integer businessID, Integer employeeID) {
        for(UserBusinessEmployee ube:user.getUserBusinessEmployees()) {
            Business b = ube.getBusiness();
            Integer e = ube.getEmployeeID();
            if(b != null && businessID.equals(b.getId()) && e != null && employeeID.equals(e))
                return ube;
        }
        return null; // else
    }
}
