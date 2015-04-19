package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
import smartshift.common.hibernate.dao.tasks.namedquery.ListNamedQueryTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class EmployeeDAO extends BaseBusinessDAO<EmployeeModel> {
    private static SmartLogger logger = new SmartLogger(EmployeeDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public EmployeeDAO(BusinessDAOContext context) {
        super(context, EmployeeModel.class);
    }
    
    /**
     * get a task that Adds a EmployeeModel
     * @param id
     * @param firstName 
     * @param lastName 
     * @param defaultGroupID 
     * @return the task object
     */
    public AddTask<EmployeeModel> add(Integer id, String firstName, String lastName, Integer defaultGroupID) {
        EmployeeModel model = new EmployeeModel();
        model.setId(id);
        model.setFirstName(firstName);
        model.setLastName(lastName);
        model.setDefaultGroupID(defaultGroupID);
        return add(model);
    }

    /** get a task that gets a list of employees in a group
     * @param groupID the group in question
     * @return the task object
     */
    public ListNamedQueryTask<EmployeeModel> listByGroup(Integer groupID) {
        return listNamedQuery(EmployeeModel.GET_GROUP_EMPLOYEES,
                new NamedParameter(EmployeeModel.GET_GROUP_EMPLOYEES_GROUP_ID, groupID));
    }

    /** get a task that gets a list of employees in a group role
     * @param groupID the group
     * @param roleID the role
     * @return the task object
     */
    public ListNamedQueryTask<EmployeeModel> listByGroupRole(Integer groupID, Integer roleID) {
        return listNamedQuery(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES,
                new NamedParameter(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_GROUP_ID, groupID),
                new NamedParameter(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_ROLE_ID, roleID));
    }

    /** get a task that gets a list of employees in a group role
     * @param groupRoleID the group role
     * @return the task object
     */
    public ListNamedQueryTask<EmployeeModel> listByGroupRoleID(Integer groupRoleID) {
        return listNamedQuery(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_BY_GR,
                new NamedParameter(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_BY_GR_GR_ID, groupRoleID));
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}