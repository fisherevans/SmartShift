package smartshift.business.hibernate.dao.business;

import java.util.List;
import smartshift.business.hibernate.model.business.EmployeeModel;
import smartshift.business.hibernate.model.business.GroupModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class EmployeeDAO extends BaseBusinessDAO {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(EmployeeDAO.class);
    
    /**
     * Fetch a EmployeeModel by id
     * @param id the id to lookup
     * @return the EmployeeModel - null if not found
     */
    public static EmployeeModel getEmployeeById(Integer id) {
        logger.debug("EmployeeDAO.getEmployeeById() Enter - " + id);
        EmployeeModel employeeModel = GenericHibernateUtil.unique(getBusinessSession(), EmployeeModel.class, id);
        logger.debug("EmployeeDAO.getEmployeeById() Got " + (employeeModel == null ? "null" : employeeModel.getFirstName() + " " + employeeModel.getLastName()));
        return employeeModel;
    }
    
    /**
     * Get all EmployeeModels
     * @return the list of EmployeeModels
     */
    public static List<EmployeeModel> getEmployee() {
        logger.debug("EmployeeDAO.getEmployee() Enter");
        List<EmployeeModel> employeeModels = GenericHibernateUtil.list(getBusinessSession(), EmployeeModel.class);
        logger.debug("EmployeeDAO.getEmployee() Got EmployeeModel count: " + employeeModels.size());
        return employeeModels;
    }
    
    /**
     * Add a EmployeeModel
     * @param firstName 
     * @param lastName 
     * @param defaultGroup 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public static EmployeeModel addEmployee(String firstName, String lastName, GroupModel defaultGroup) throws DBException {
        logger.debug("EmployeeDAO.addEmployee() Enter");
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setFirstName(firstName);
        employeeModel.setLastName(lastName);
        employeeModel.setDefaultGroup(defaultGroup);
        GenericHibernateUtil.save(getBusinessSession(), employeeModel);
        logger.debug("EmployeeDAO.addEmployee() Success");
        return employeeModel;
    }
}