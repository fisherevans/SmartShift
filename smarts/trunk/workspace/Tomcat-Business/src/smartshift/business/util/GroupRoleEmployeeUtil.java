package smartshift.business.util;

import smartshift.business.cache.bo.Cache;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Utility methods for access and controlling GRE objects
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
public class GroupRoleEmployeeUtil {
    private static final SmartLogger logger = new SmartLogger(GroupRoleEmployeeUtil.class);
    
    /**
     * Gets a Group BO
     * @param cashe The cache to use
     * @param employee the employee requesting access
     * @param groupID the group id
     * @param requireManages set true if should return null if group is not managed by employee
     * @return the group. null if invalid id. null if requireManages is true and employee does not manage it
     */
    public static Group getGroup(Cache cashe, Employee employee, Integer groupID, boolean requireManages) {
        logger.debug("getGroup() Enter");
        Group group = Group.load(cashe, groupID);
        if(group == null)
            return null;
        if(requireManages && !employee.manages(group))
            return null;
        logger.debug("getGroup() Valid group found");
        return group;
    }
}
