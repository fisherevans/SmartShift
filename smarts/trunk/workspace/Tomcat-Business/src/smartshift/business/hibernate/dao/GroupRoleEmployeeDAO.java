package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.GroupRoleEmployeeModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GroupRoleEmployeeDAO extends BaseBusinessDAO<GroupRoleEmployeeModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupRoleEmployeeDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupRoleEmployeeDAO(DAOContext context) {
        super(context, GroupRoleEmployeeModel.class);
    }
    
    /** Links an group role to an employee
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @throws DBException if there's an error
     */
    public void link(Integer groupRoleID, Integer employeeID) throws DBException {
        if(isLinked(groupRoleID, employeeID))
            return;
        GroupRoleEmployeeModel model = new GroupRoleEmployeeModel();
        model.setGroupRoleID(groupRoleID);
        model.setEmployeeID(employeeID);
        add(model);
    }

    /** Unlinks an employee from a group role
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @throws DBException if there's an error
     */
    public void unlink(Integer groupRoleID, Integer employeeID) throws DBException {
        GroupRoleEmployeeModel model = uniqueByGroupRoleEmployee(groupRoleID, employeeID);
        if(model != null)
            delete(model);
    }
    
    /** Checks to see if an employee is linked to a group role
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @return true if link exists
     * @throws DBException if there's an error
     */
    public boolean isLinked(Integer groupRoleID, Integer employeeID) throws DBException {
        GroupRoleEmployeeModel model = uniqueByGroupRoleEmployee(groupRoleID, employeeID);
        return model != null;
    }
    
    /** gets the model of a group role linked to a group
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @return the link model if it exists
     * @throws DBException if there's an error
     */
    public GroupRoleEmployeeModel uniqueByGroupRoleEmployee(Integer groupRoleID, Integer employeeID) throws DBException {
        GroupRoleEmployeeModel model = uniqueByCriteria(
                Restrictions.eq("groupRoleID", groupRoleID),
                Restrictions.eq("employeeID", employeeID));
        return model;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}