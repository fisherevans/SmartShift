package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.GroupRoleModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
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
    
    /** Links an employee to a group
     * @param groupID the group id
     * @param roleID the role id
     * @throws DBException if there's an error
     */
    public void link(Integer groupID, Integer roleID) throws DBException {
        logger.debug("Linking G" + groupID + " R" + roleID);
        if(isLinked(groupID, roleID))
            return;
        GroupRoleModel model = new GroupRoleModel();
        model.setGroupID(groupID);
        model.setRoleID(roleID);
        add(model);
    }

    /** Unlinks an employee from a group
     * @param groupID the group id
     * @param roleID the role id
     * @throws DBException if there's an error
     */
    public void unlink(Integer groupID, Integer roleID) throws DBException {
        logger.debug("Unlinking G" + groupID + " R" + roleID);
        GroupRoleModel model = uniqueByGroupRole(groupID, roleID);
        if(model != null)
            delete(model);
    }
    
    /** Checks to see if an employee is linked to a group
     * @param groupID the group id
     * @param roleID the role id
     * @return true if link exists
     * @throws DBException if there's an error
     */
    public boolean isLinked(Integer groupID, Integer roleID) throws DBException {
        GroupRoleModel model = uniqueByGroupRole(groupID, roleID);
        return model != null;
    }
    
    /** gets the model of a role linked to a group
     * @param groupID the group id
     * @param roleID the role id
     * @return the link model if it exists
     * @throws DBException if there's an error
     */
    public GroupRoleModel uniqueByGroupRole(Integer groupID, Integer roleID) throws DBException {
        GroupRoleModel model = uniqueByCriteria(
                Restrictions.eq("groupID", groupID),
                Restrictions.eq("roleID", roleID));
        return model;
    }
    
    /** gets the group roles that belong to a user
     * @param employeeID the employee
     * @return the ro list of gr's
     */
    public ROCollection<GroupRoleModel> listByEmployee(Integer employeeID) {
        ROCollection<GroupRoleModel> models = listNamedQuery(GroupRoleModel.GET_GROUP_ROLES_BY_EMPLOYEE,
                new NamedParameter(GroupRoleModel.GET_GROUP_ROLES_BY_EMPLOYEE_EMP_ID, employeeID));
        return models;
    }
    
    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}