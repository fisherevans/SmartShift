package smartshift.business.hibernate.dao;

import java.util.List;
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
public class GroupDAO extends BaseBusinessDAO {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupDAO(DAOContext context) {
        super(context);
    }
    
    /**
     * Fetch a EmployeeModel by id
     * @param id the id to lookup
     * @return the EmployeeModel - null if not found
     */
    public GroupModel getGroupById(Integer id) {
        logger.debug("GroupDAO.getGroupById() Enter - " + id);
        GroupModel groupModel = GenericHibernateUtil.unique(getBusinessSession(), GroupModel.class, id);
        logger.debug("GroupDAO.getGroupById() Got " + (groupModel == null ? "null" : groupModel.getName()));
        return groupModel;
    }
    
    /**
     * Get all EmployeeModels
     * @return the list of EmployeeModels
     */
    public List<GroupModel> getGroups() {
        logger.debug("GroupDAO.getGroup() Enter");
        List<GroupModel> groupModels = GenericHibernateUtil.list(getBusinessSession(), GroupModel.class);
        logger.debug("GroupDAO.getGroup() Got GroupModel count: " + groupModels.size());
        return groupModels;
    }
    
    /**
     * Add a EmployeeModel
     * @param name 
     * @param parentID 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public GroupModel addGroup(String name, Integer parentID) throws DBException {
        logger.debug("GroupDAO.addGroup() Enter");
        GroupModel groupModel = new GroupModel();
        groupModel.setName(name);
        groupModel.setParentID(parentID);
        GenericHibernateUtil.save(getBusinessSession(), groupModel);
        logger.debug("GroupDAO.addGroup() Success");
        return groupModel;
    }

    /** gets a list of groups an employee belongs to
     * @param employeeID the employee in question
     * @return the list of employees ids
     */
    public ROList<GroupModel> getEmployeeGroups(Integer employeeID) {
        @SuppressWarnings("unchecked")
        List<GroupModel> groups = getBusinessSession()
                .getNamedQuery(GroupModel.GET_EMPLOYEE_GROUPS)
                .setParameter(GroupModel.GET_EMPLOYEE_GROUPS_EMP_ID, employeeID)
                .list();
        return new ROList<GroupModel>(groups);
    }
}