package smartshift.common.hibernate.dao.business;

import java.util.List;
import smartshift.common.hibernate.DBException;
import smartshift.common.hibernate.model.business.GroupModel;
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
     * Fetch a EmployeeModel by id
     * @param id the id to lookup
     * @return the EmployeeModel - null if not found
     */
    public static GroupModel getGroupById(Integer id) {
        logger.debug("GroupDAO.getGroupById() Enter - " + id);
        GroupModel groupModel = GenericHibernateUtil.unique(getBusinessSession(), GroupModel.class, id);
        logger.debug("GroupDAO.getGroupById() Got " + (groupModel == null ? "null" : groupModel.getName()));
        return groupModel;
    }
    
    /**
     * Get all EmployeeModels
     * @return the list of EmployeeModels
     */
    public static List<GroupModel> getGroup() {
        logger.debug("GroupDAO.getGroup() Enter");
        List<GroupModel> groupModels = GenericHibernateUtil.list(getBusinessSession(), GroupModel.class);
        logger.debug("GroupDAO.getGroup() Got GroupModel count: " + groupModels.size());
        return groupModels;
    }
    
    /**
     * Add a EmployeeModel
     * @param name 
     * @param parent 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public static GroupModel addGroup(String name, GroupModel parent) throws DBException {
        logger.debug("GroupDAO.addGroup() Enter");
        GroupModel groupModel = new GroupModel();
        groupModel.setName(name);
        groupModel.setParent(parent);
        GenericHibernateUtil.save(getBusinessSession(), groupModel);
        logger.debug("GroupDAO.addGroup() Success");
        return groupModel;
    }
}