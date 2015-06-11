package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.RoleScheduleModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the ShiftModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class RoleScheduleDAO extends BaseBusinessDAO<RoleScheduleModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(RoleScheduleDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public RoleScheduleDAO(BusinessDAOContext context) {
        super(context, RoleScheduleModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}