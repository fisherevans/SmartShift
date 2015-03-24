package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.GroupRoleModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.DeleteByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.ListNamedQueryTask;
import smartshift.common.hibernate.dao.tasks.ListTask;
import smartshift.common.hibernate.dao.tasks.RowCountTask;
import smartshift.common.hibernate.dao.tasks.UniqueByCriteriaTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GroupRoleDAO extends BaseBusinessDAO<GroupRoleModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupRoleDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupRoleDAO(BusinessDAOContext context) {
        super(context, GroupRoleModel.class);
    }
    
    /** get a task that counts links between a role and a group
     * @param groupID the group id
     * @param roleID the role id
     * @return the task object
     */
    public RowCountTask<GroupRoleModel> linkCount(Integer groupID, Integer roleID) {
        return rowCount(getGroupRoleCriterion(groupID, roleID));
    }
    
    /** get a task that Links a role to a group
     * @param groupID the group id
     * @param roleID the role id
     * @return the task object
     */
    public AddTask<GroupRoleModel> link(Integer groupID, Integer roleID) {
        GroupRoleModel model = new GroupRoleModel();
        model.setGroupID(groupID);
        model.setRoleID(roleID);
        return add(model);
    }

    /** get a task that Unlinks a role from a group
     * @param groupID the group id
     * @param roleID the role id
     * @return the task object
     */
    public DeleteByCriteriaTask<GroupRoleModel> unlink(Integer groupID, Integer roleID) {
        return deleteByCriteria(getGroupRoleCriterion(groupID, roleID));
    }
    
    /** get a task that gets the model of a role linked to a group
     * @param groupID the group id
     * @param roleID the role id
     * @return the task object
     */
    public UniqueByCriteriaTask<GroupRoleModel> uniqueByGroupRole(Integer groupID, Integer roleID) {
        return uniqueByCriteria(getGroupRoleCriterion(groupID, roleID));
    }
    
    /** gets a task that gets a list of GRs with a group id
     * @param groupID the group id
     * @return the task
     */
    public ListTask<GroupRoleModel> listByGroup(Integer groupID) {
        return list(Restrictions.eq("groupID", groupID));
    }
    
    /** get a task that gets the group roles that belong to a user
     * @param employeeID the employee
     * @return the task object
     */
    public ListNamedQueryTask<GroupRoleModel> listByEmployee(Integer employeeID) {
        return listNamedQuery(GroupRoleModel.GET_GROUP_ROLES_BY_EMPLOYEE,
                new NamedParameter(GroupRoleModel.GET_GROUP_ROLES_BY_EMPLOYEE_EMP_ID, employeeID));
    }
    
    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
    
    private Criterion[] getGroupRoleCriterion(Integer groupID, Integer roleID) {
        return new Criterion[] { Restrictions.eq("groupID", groupID), Restrictions.eq("roleID", roleID) };
    }
}