package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.GroupRoleEmployeeModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GroupRoleEmployeeDAO extends BaseBusinessDAO {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupRoleEmployeeDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupRoleEmployeeDAO(DAOContext context) {
        super(context);
    }
    
    /** Links an employee to a group role link
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @throws DBException if there's an error
     */
    public void linkGroupRoleEmployee(Integer groupRoleID, Integer employeeID) throws DBException {
        logger.debug("Linking GRE" + groupRoleID + " E" + employeeID);
        if(isGroupRoleEmployeeLinked(groupRoleID, employeeID))
            return;
        GroupRoleEmployeeModel link = new GroupRoleEmployeeModel();
        link.setGroupRoleID(groupRoleID);
        link.setEmployeeID(employeeID);
        GenericHibernateUtil.save(getBusinessSession(), link);
    }

    /** Unlinks an employee to a group role link
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @throws DBException if there's an error
     */
    public void unlinkGroupEmployeeEmployee(Integer groupRoleID, Integer employeeID) throws DBException {
        logger.debug("Unlinking GRE" + groupRoleID + " E" + employeeID);
        GroupRoleEmployeeModel link = getGroupRoleEmployeeLink(groupRoleID, employeeID);
        if(link != null)
            GenericHibernateUtil.delete(getBusinessSession(), link);
    }
    
    /** Checks to see if an employee is linked to a group role
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @return true if link exists
     * @throws DBException if there's an error
     */
    public boolean isGroupRoleEmployeeLinked(Integer groupRoleID, Integer employeeID) throws DBException {
        return getGroupRoleEmployeeLink(groupRoleID, employeeID) != null;
    }
    
    /** gets the model of a employee linked to a group role
     * @param groupRoleID the group role id
     * @param employeeID the employee id
     * @return the link model if it exists
     * @throws DBException if there's an error
     */
    public GroupRoleEmployeeModel getGroupRoleEmployeeLink(Integer groupRoleID, Integer employeeID) throws DBException {
        GroupRoleEmployeeModel link = GenericHibernateUtil.uniqueByCriterea(getBusinessSession(), GroupRoleEmployeeModel.class,
                Restrictions.eq("groupRoleID", groupRoleID), Restrictions.eq("employeeID", employeeID));
        return link;
    }
}