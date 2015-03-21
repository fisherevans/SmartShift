package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.ListNamedQueryTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class RoleDAO extends BaseBusinessDAO<RoleModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(RoleDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public RoleDAO(BusinessDAOContext context) {
        super(context, RoleModel.class);
    }
    
    /**
     * Get a task that get all roles for an employee and group 
     * @param groupID  the group
     * @param employeeID the employee
     * @return the task object
     */
    public ListNamedQueryTask<RoleModel> listByGroupEmployee(Integer groupID, Integer employeeID) {
        return listNamedQuery(RoleModel.GET_EMPLOYEE_GROUP_ROLES,
                new NamedParameter(RoleModel.GET_EMPLOYEE_GROUP_ROLES_EMP_ID, employeeID),
                new NamedParameter(RoleModel.GET_EMPLOYEE_GROUP_ROLES_GRP_ID, groupID));
    }

    /** get a task that gets a list of roles belonging to a group
     * @param groupID the group
     * @return the task object
     */
    public ListNamedQueryTask<RoleModel> listByGroup(Integer groupID) {
        return listNamedQuery(RoleModel.GET_GROUP_ROLES,
                new NamedParameter(RoleModel.GET_GROUP_ROLES_GRP_ID, groupID));
    }
    
    /**
     * get a task that Adds a Role
     * @param name 
     * @return the task object
     */
    public AddTask<RoleModel> add(String name) {
        RoleModel model = new RoleModel();
        model.setName(name);
        return add(model);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}