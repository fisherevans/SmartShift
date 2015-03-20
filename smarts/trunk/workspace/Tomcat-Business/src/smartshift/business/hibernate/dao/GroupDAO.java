package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.ListNamedQueryTask;
import smartshift.common.hibernate.dao.tasks.ListTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GroupDAO extends BaseBusinessDAO<GroupModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupDAO(BusinessDAOContext context) {
        super(context, GroupModel.class);
    }
    
    /**
     * get a task that adds a EmployeeModel
     * @param name 
     * @param parentID 
     * @return the task object
     */
    public AddTask<GroupModel> add(String name, Integer parentID) {
        GroupModel model = new GroupModel();
        model.setName(name);
        model.setParentID(parentID);
        return add(model);
    }
    
    /** get a task that gets a list of groups an employee belongs to
     * @param employeeID the employee in question
     * @return the task
     */
    public ListNamedQueryTask<GroupModel> listByEmployee(Integer employeeID) {
        return listNamedQuery(GroupModel.GET_EMPLOYEE_GROUPS, 
                new NamedParameter(GroupModel.GET_EMPLOYEE_GROUPS_EMP_ID, employeeID));
    }
    
    /** get a task that gets a list of groups that has a role
     * @param roleID the role in question
     * @return the task
     */
    public ListNamedQueryTask<GroupModel> listByRole(Integer roleID) {
        return listNamedQuery(GroupModel.GET_ROLE_GROUPS, 
                new NamedParameter(GroupModel.GET_ROLE_GROUPS_ROLE_ID, roleID));
    }
    
    /**
     * get a task that gets a list of groups that have a specific parent
     * @param parentID the id of the parent to check
     * @return the task
     */
    public ListTask<GroupModel> listChildGroups(Integer parentID) {
        return list(Restrictions.eq("parentID", parentID));
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}