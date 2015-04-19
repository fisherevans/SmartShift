package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.GroupEmployeeModel;
import smartshift.business.hibernate.model.GroupEmployeeModelId;
import smartshift.common.hibernate.dao.tasks.criteria.CountByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.id.DeleteByIDTask;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GroupEmployeeDAO extends BaseBusinessDAO<GroupEmployeeModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupEmployeeDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupEmployeeDAO(BusinessDAOContext context) {
        super(context, GroupEmployeeModel.class);
    }
    
    /** get a task that Links an employee to a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @return the task object
     */
    public AddTask<GroupEmployeeModel> link(Integer groupID, Integer employeeID) {
        GroupEmployeeModel model = new GroupEmployeeModel();
        model.setGroupID(groupID);
        model.setEmployeeID(employeeID);
        return add(model);
    }
    
    /** get a task that counts links between an employee and a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @return the task object
     */
    public CountByCriteriaTask<GroupEmployeeModel> linkCount(Integer groupID, Integer employeeID) {
        return rowCount(getGroupEmployeeCriterion(groupID, employeeID));
    }

    /** get a task that Unlinks an employee from a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @return the task object
     */
    public DeleteByIDTask<GroupEmployeeModel> unlink(Integer groupID, Integer employeeID) {
        return deleteByID(new GroupEmployeeModelId(groupID, employeeID));
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
    
    private Criterion[] getGroupEmployeeCriterion(Integer groupID, Integer employeeID) {
        return new Criterion[] { Restrictions.eq("groupID", groupID), Restrictions.eq("employeeID", employeeID) };
    }
}