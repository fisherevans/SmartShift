package smartshift.business.hibernate.dao;

import java.util.List;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROList;
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
     * @param context Base context for this business DAO
     */
    public EmployeeDAO(DAOContext context) {
        super(context);
    }
    
    /**
     * Fetch a EmployeeModel by id
     * @param id the id to lookup
     * @return the EmployeeModel - null if not found
     */
    public EmployeeModel getEmployeeById(Integer id) {
        logger.debug("EmployeeDAO.getEmployeeById() Enter - " + id);
        EmployeeModel employeeModel = GenericHibernateUtil.unique(getBusinessSession(), EmployeeModel.class, id);
        logger.debug("EmployeeDAO.getEmployeeById() Got " + (employeeModel == null ? "null" : employeeModel.getFirstName() + " " + employeeModel.getLastName()));
        return employeeModel;
    }
    
    /**
     * Get all EmployeeModels
     * @return the list of EmployeeModels
     */
    public List<EmployeeModel> getEmployees() {
        logger.debug("EmployeeDAO.getEmployee() Enter");
        List<EmployeeModel> employeeModels = GenericHibernateUtil.list(getBusinessSession(), EmployeeModel.class);
        logger.debug("EmployeeDAO.getEmployee() Got EmployeeModel count: " + employeeModels.size());
        return employeeModels;
    }
    
    /**
     * Add a EmployeeModel
     * @param firstName 
     * @param lastName 
     * @param defaultGroupID 
     * @return the created EmployeeModel
     * @throws DBException if there was an error creating the EmployeeModel
     */
    public EmployeeModel addEmployee(String firstName, String lastName, Integer defaultGroupID) throws DBException {
        logger.debug("EmployeeDAO.addEmployee() Enter");
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setFirstName(firstName);
        employeeModel.setLastName(lastName);
        employeeModel.setDefaultGroupID(defaultGroupID);
        GenericHibernateUtil.save(getBusinessSession(), employeeModel);
        logger.debug("EmployeeDAO.addEmployee() Success");
        return employeeModel;
    }

    /** gets a list of employees in a group
     * @param groupID the group in question
     * @return the list of employees ids
     */
    public ROList<EmployeeModel> getGroupEmployees(Integer groupID) {
        @SuppressWarnings("unchecked")
        List<EmployeeModel> employees = getBusinessSession()
                .getNamedQuery(EmployeeModel.GET_GROUP_EMPLOYEES)
                .setParameter(EmployeeModel.GET_GROUP_EMPLOYEES_GROUP_ID, groupID)
                .list();
        return new ROList<EmployeeModel>(employees);
    }
}