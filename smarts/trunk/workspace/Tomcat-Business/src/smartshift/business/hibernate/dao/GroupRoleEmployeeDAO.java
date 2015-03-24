package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.GroupRoleEmployeeModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.DeleteByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.RowCountTask;
import smartshift.common.hibernate.dao.tasks.UniqueByCriteriaTask;
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
    public GroupRoleEmployeeDAO(BusinessDAOContext context) {
        super(context, GroupRoleEmployeeModel.class);
    }
    
    /** get a task to get Links an group role to an employee
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @return the task object
     */
    public AddTask<GroupRoleEmployeeModel> link(Integer groupRoleID, Integer employeeID) {
        GroupRoleEmployeeModel model = new GroupRoleEmployeeModel();
        model.setGroupRoleID(groupRoleID);
        model.setEmployeeID(employeeID);
        return add(model);
    }
    
    /** get a task that Unlinks an employee from a group role
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @return the task object
     */
    public DeleteByCriteriaTask<GroupRoleEmployeeModel> unlink(Integer groupRoleID, Integer employeeID) {
        return deleteByCriteria(getGroupRoleEmployeeCriterion(groupRoleID, employeeID));
    }
    
    /**
     * get a task that counts links between a group role and an employee
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @return the task object
     */
    public RowCountTask<GroupRoleEmployeeModel> linkCount(Integer groupRoleID, Integer employeeID) {
        return rowCount(getGroupRoleEmployeeCriterion(groupRoleID, employeeID));
    }
    
    /** get a task that gets the model of a group role linked to an employee
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @return the task object
     */
    public UniqueByCriteriaTask<GroupRoleEmployeeModel> uniqueByGroupRoleEmployee(Integer groupRoleID, Integer employeeID) {
        return uniqueByCriteria(getGroupRoleEmployeeCriterion(groupRoleID, employeeID));
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
    
    private Criterion[] getGroupRoleEmployeeCriterion(Integer groupRoleID, Integer employeeID) {
        return new Criterion[] { Restrictions.eq("groupRoleID", groupRoleID), Restrictions.eq("employeeID", employeeID) };
    }
}