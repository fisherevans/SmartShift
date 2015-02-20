package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
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
    public RoleDAO(DAOContext context) {
        super(context, RoleModel.class);
    }
    
    /**
     * Get all roles for an employee and group 
     * @param groupID  the group
     * @param employeeID the employee
     * @return the list of roles
     */
    public ROCollection<RoleModel> listByGroupEmployee(Integer groupID, Integer employeeID) {
        ROCollection<RoleModel> models = listNamedQuery(RoleModel.GET_EMPLOYEE_GROUP_ROLES,
                new NamedParameter(RoleModel.GET_EMPLOYEE_GROUP_ROLES_EMP_ID, employeeID),
                new NamedParameter(RoleModel.GET_EMPLOYEE_GROUP_ROLES_GRP_ID, groupID));
        return models;
    }
    
    /** gets a list of roles belonging to a group
     * @param groupID the group
     * @return the ro list of roles
     */
    public ROCollection<RoleModel> listByGroup(Integer groupID) {
        ROCollection<RoleModel> models = listNamedQuery(RoleModel.GET_GROUP_ROLES,
                new NamedParameter(RoleModel.GET_GROUP_ROLES_GRP_ID, groupID));
        return models;
    }
    
    /**
     * Add a Role
     * @param name 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public RoleModel addRole(String name) throws DBException {
        RoleModel model = new RoleModel();
        model.setName(name);
        model = add(model);
        return model;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}