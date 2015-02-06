package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.GroupEmployeeModel;
import smartshift.business.hibernate.model.GroupEmployeeModelId;
import smartshift.business.hibernate.model.GroupRoleModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GroupRoleDAO extends BaseBusinessDAO {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupRoleDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupRoleDAO(DAOContext context) {
        super(context);
    }
    
    /** Links an employee to a group
     * @param groupID the group id
     * @param roleID the role id
     * @throws DBException if there's an error
     */
    public void linkGroupRole(Integer groupID, Integer roleID) throws DBException {
        logger.debug("Linking G" + groupID + " R" + roleID);
        if(isGroupRoleLinked(groupID, roleID))
            return;
        GroupRoleModel link = new GroupRoleModel();
        link.setGroupID(groupID);
        link.setRoleID(roleID);
        GenericHibernateUtil.save(getBusinessSession(), link);
    }

    /** Unlinks an employee from a group
     * @param groupID the group id
     * @param roleID the role id
     * @throws DBException if there's an error
     */
    public void unlinkGroupEmployee(Integer groupID, Integer roleID) throws DBException {
        logger.debug("Unlinking G" + groupID + " R" + roleID);
        GroupRoleModel link = GenericHibernateUtil.uniqueByCriterea(getBusinessSession(), GroupRoleModel.class,
                Restrictions.eq("groupID", groupID), Restrictions.eq("roleID", roleID));
        if(link != null)
            GenericHibernateUtil.delete(getBusinessSession(), link);
    }
    
    /** Checks to see if a role is linked to a group
     * @param groupID the group id
     * @param roleID the role id
     * @return true if link exists
     * @throws DBException if there's an error
     */
    public boolean isGroupRoleLinked(Integer groupID, Integer roleID) throws DBException {
        return getGroupRoleLink(groupID, roleID) != null;
    }
    
    /** gets the model of a role linked to a group
     * @param groupID the group id
     * @param roleID the role id
     * @return the link model if it exists
     * @throws DBException if there's an error
     */
    public GroupRoleModel getGroupRoleLink(Integer groupID, Integer roleID) throws DBException {
        GroupRoleModel link = GenericHibernateUtil.uniqueByCriterea(getBusinessSession(), GroupRoleModel.class,
                Restrictions.eq("groupID", groupID), Restrictions.eq("roleID", roleID));
        return link;
    }
}