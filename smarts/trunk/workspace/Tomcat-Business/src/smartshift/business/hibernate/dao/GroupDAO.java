package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
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
     * Add a EmployeeModel
     * @param name 
     * @param parentID 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public GroupModel add(String name, Integer parentID) throws DBException {
        logger.debug("GroupDAO.addGroup() Enter");
        GroupModel model = new GroupModel();
        model.setName(name);
        model.setParentID(parentID);
        model = add(model);
        logger.debug("GroupDAO.addGroup() Success");
        return model;
    }
    
    /** gets a list of groups an employee belongs to
     * @param employeeID the employee in question
     * @return the list of employees ids
     */
    public ROCollection<GroupModel> listByEmployee(Integer employeeID) {
        ROCollection<GroupModel> models = listNamedQuery(GroupModel.GET_EMPLOYEE_GROUPS, 
                new NamedParameter(GroupModel.GET_EMPLOYEE_GROUPS_EMP_ID, employeeID));
        return models;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}