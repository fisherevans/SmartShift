package smartshift.common.hibernate.dao.business;

import java.util.List;
import smartshift.common.hibernate.DBException;
import smartshift.common.hibernate.model.business.RoleModel;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class RoleDAO extends BaseBusinessDAO {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(RoleDAO.class);
    
    /**
     * Fetch a EmployeeModel by id
     * @param id the id to lookup
     * @return the EmployeeModel - null if not found
     */
    public static RoleModel getRoleById(Integer id) {
        logger.debug("RoleDAO.getRoleById() Enter - " + id);
        RoleModel roleModel = GenericHibernateUtil.unique(getBusinessSession(), RoleModel.class, id);
        logger.debug("RoleDAO.getRoleById() Got " + (roleModel == null ? "null" : roleModel.getName()));
        return roleModel;
    }
    
    /**
     * Get all EmployeeModels
     * @return the list of EmployeeModels
     */
    public static List<RoleModel> getRole() {
        logger.debug("RoleDAO.getRole() Enter");
        List<RoleModel> roleModels = GenericHibernateUtil.list(getBusinessSession(), RoleModel.class);
        logger.debug("RoleDAO.getRole() Got RoleModel count: " + roleModels.size());
        return roleModels;
    }
    
    /**
     * Add a EmployeeModel
     * @param name 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public static RoleModel addRole(String name) throws DBException {
        logger.debug("RoleDAO.addRole() Enter");
        RoleModel roleModel = new RoleModel();
        roleModel.setName(name);
        GenericHibernateUtil.save(getBusinessSession(), roleModel);
        logger.debug("RoleDAO.addRole() Success");
        return roleModel;
    }
}