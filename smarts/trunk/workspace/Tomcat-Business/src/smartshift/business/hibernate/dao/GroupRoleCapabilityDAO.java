package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.GroupRoleCapabilityID;
import smartshift.business.hibernate.model.GroupRoleCapabilityModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.DeleteByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.DeleteByIDTask;
import smartshift.common.hibernate.dao.tasks.ListTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class GroupRoleCapabilityDAO extends BaseBusinessDAO<GroupRoleCapabilityModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(GroupRoleCapabilityDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public GroupRoleCapabilityDAO(BusinessDAOContext context) {
        super(context, GroupRoleCapabilityModel.class);
    }
    
    /** gets a task that gets a list of GRs with a group id
     * @param groupRoleID the groupRoleID id
     * @return the task
     */
    public ListTask<GroupRoleCapabilityModel> listByGroupRole(Integer groupRoleID) {
        return list(Restrictions.eq("groupRoleID", groupRoleID));
    }

    /** get a task that Unlinks an employee from a group
     * @param groupID the group id
     * @param employeeID the employee id
     * @return the task object
     */
    public AddTask<GroupRoleCapabilityModel> add(Integer groupRoleID, Integer capability) {
        GroupRoleCapabilityModel model = new GroupRoleCapabilityModel();
        model.setGroupRoleID(groupRoleID);
        model.setCapabilityID(capability);
        return add(model);
    }
    
    public DeleteByIDTask<GroupRoleCapabilityModel> remove(Integer groupRoleID, Integer capability) {
        return deleteByID(new GroupRoleCapabilityID(groupRoleID, capability));
    }
    
    public DeleteByCriteriaTask<GroupRoleCapabilityModel> removeAll(Integer groupRoleID) {
        return deleteByCriteria(Restrictions.eq("groupRoleID", groupRoleID));
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}