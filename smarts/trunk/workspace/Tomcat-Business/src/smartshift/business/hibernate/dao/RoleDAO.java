package smartshift.business.hibernate.dao;

import java.util.List;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROList;
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
     * @param context Base context for this business DAO
     */
    public RoleDAO(DAOContext context) {
        super(context);
    }
    
    /**
     * Fetch a EmployeeModel by id
     * @param id the id to lookup
     * @return the EmployeeModel - null if not found
     */
    public RoleModel getRoleById(Integer id) {
        logger.debug("RoleDAO.getRoleById() Enter - " + id);
        RoleModel roleModel = GenericHibernateUtil.unique(getBusinessSession(), RoleModel.class, id);
        logger.debug("RoleDAO.getRoleById() Got " + (roleModel == null ? "null" : roleModel.getName()));
        return roleModel;
    }
    
    /**
     * Get all EmployeeModels
     * @return the list of EmployeeModels
     */
    public List<RoleModel> getRoles() {
        logger.debug("RoleDAO.getRole() Enter");
        List<RoleModel> roleModels = GenericHibernateUtil.list(getBusinessSession(), RoleModel.class);
        logger.debug("RoleDAO.getRole() Got RoleModel count: " + roleModels.size());
        return roleModels;
    }
    
    /**
     * Get all EmployeeModels
     * @return the list of EmployeeModels
     */
    public ROList<RoleModel> getEmployeeGroupRoles(Integer employeeID, Integer groupID) {
        logger.debug("RoleDAO.getEmployeeGroupRoles() Enter");
        @SuppressWarnings("unchecked")
        List<RoleModel> roleModels = getBusinessSession()
                .getNamedQuery(RoleModel.GET_EMPLOYEE_GROUP_ROLES)
                .setParameter(RoleModel.GET_EMPLOYEE_GROUP_ROLES_EMP_ID, employeeID)
                .setParameter(RoleModel.GET_EMPLOYEE_GROUP_ROLES_GRP_ID, groupID)
                .list();
        logger.debug("RoleDAO.getEmployeeGroupRoles() Got RoleModel count: " + roleModels.size());
        return new ROList<RoleModel>(roleModels);
    }
    
    /**
     * Add a EmployeeModel
     * @param name 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public RoleModel addRole(String name) throws DBException {
        logger.debug("RoleDAO.addRole() Enter");
        RoleModel roleModel = new RoleModel();
        roleModel.setName(name);
        GenericHibernateUtil.save(getBusinessSession(), roleModel);
        logger.debug("RoleDAO.addRole() Success");
        return roleModel;
    }
}