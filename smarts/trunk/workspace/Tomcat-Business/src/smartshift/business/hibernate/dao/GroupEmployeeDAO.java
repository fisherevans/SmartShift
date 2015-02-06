package smartshift.business.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.GroupEmployeeModel;
import smartshift.business.hibernate.model.GroupEmployeeModelId;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROList;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GroupEmployeeDAO extends BaseBusinessDAO {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupEmployeeDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupEmployeeDAO(DAOContext context) {
        super(context);
    }
    
    /** Links an employee to a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @throws DBException if there's an error
     */
    public void linkGroupEmployee(Integer groupID, Integer employeeID) throws DBException {
        GroupEmployeeModel link = new GroupEmployeeModel();
        link.setGroupID(groupID);
        link.setEmployeeID(employeeID);
        GenericHibernateUtil.save(getBusinessSession(), link);
    }

    /** Unlinks an employee from a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @throws DBException if there's an error
     */
    public void unlinkGroupEmployee(Integer groupID, Integer employeeID) throws DBException {
        GroupEmployeeModelId id = new GroupEmployeeModelId();
        id.groupID = groupID;
        id.employeeID = employeeID;
        GroupEmployeeModel link = GenericHibernateUtil.unique(getBusinessSession(), GroupEmployeeModel.class, id);
        if(link != null)
            GenericHibernateUtil.delete(getBusinessSession(), link);
    }
    
    /** Checks to see if an employee is linked to a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @return true if link exists
     * @throws DBException if there's an error
     */
    public boolean isGroupEmployeeLinked(Integer groupID, Integer employeeID) throws DBException {
        GroupEmployeeModelId id = new GroupEmployeeModelId();
        id.groupID = groupID;
        id.employeeID = employeeID;
        GroupEmployeeModel link = GenericHibernateUtil.unique(getBusinessSession(), GroupEmployeeModel.class, id);
        return link != null;
    }
}