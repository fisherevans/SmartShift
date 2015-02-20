package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
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
    public EmployeeDAO(DAOContext context) {
        super(context, EmployeeModel.class);
    }
    
    /**
     * Add a EmployeeModel
     * @param firstName 
     * @param lastName 
     * @param defaultGroupID 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public EmployeeModel add(String firstName, String lastName, Integer defaultGroupID) throws DBException {
        EmployeeModel model = new EmployeeModel();
        model.setFirstName(firstName);
        model.setLastName(lastName);
        model.setDefaultGroupID(defaultGroupID);
        model = add(model);
        return model;
    }

    /** gets a list of employees in a group
     * @param groupID the group in question
     * @return the list of employees
     */
    public ROCollection<EmployeeModel> listByGroup(Integer groupID) {
        ROCollection<EmployeeModel> models = listNamedQuery(EmployeeModel.GET_GROUP_EMPLOYEES,
                new NamedParameter(EmployeeModel.GET_GROUP_EMPLOYEES_GROUP_ID, groupID));
        return models;
    }

    /** gets a list of employees in a group role
     * @param groupID the group
     * @param roleID the role
     * @return the list of employees ids
     */
    public ROCollection<EmployeeModel> listByGroupRole(Integer groupID, Integer roleID) {
        ROCollection<EmployeeModel> models = listNamedQuery(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES,
                new NamedParameter(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_GROUP_ID, groupID),
                new NamedParameter(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_ROLE_ID, roleID));
        return models;
    }

    /** gets a list of employees in a group role
     * @param groupRoleID the group role
     * @return the list of employees ids
     */
    public ROCollection<EmployeeModel> listByGroupRoleID(Integer groupRoleID) {
        ROCollection<EmployeeModel> models = listNamedQuery(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_BY_GR,
                new NamedParameter(EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_BY_GR_GR_ID, groupRoleID));
        return models;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}