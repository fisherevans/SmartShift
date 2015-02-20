package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.GroupEmployeeModel;
import smartshift.business.hibernate.model.GroupEmployeeModelId;
import smartshift.common.hibernate.DBException;
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
    
    /** Links an employee to a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @throws DBException if there's an error
     */
    public void link(Integer groupID, Integer employeeID) throws DBException {
        logger.debug("Linking G" + groupID + " E" + employeeID);
        if(isLinked(groupID, employeeID))
            return;
        GroupEmployeeModel model = new GroupEmployeeModel();
        model.setGroupID(groupID);
        model.setEmployeeID(employeeID);
        add(model);
    }

    /** Unlinks an employee from a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @throws DBException if there's an error
     */
    public void unlink(Integer groupID, Integer employeeID) throws DBException {
        logger.debug("Unlinking G" + groupID + " E" + employeeID);
        GroupEmployeeModel model = uniqueByID(new GroupEmployeeModelId(groupID, employeeID));
        if(model != null)
            delete(model);
    }
    
    /** Checks to see if an employee is linked to a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @return true if link exists
     * @throws DBException if there's an error
     */
    public boolean isLinked(Integer groupID, Integer employeeID) throws DBException {
        GroupEmployeeModel model = uniqueByID(new GroupEmployeeModelId(groupID, employeeID));
        return model != null;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}