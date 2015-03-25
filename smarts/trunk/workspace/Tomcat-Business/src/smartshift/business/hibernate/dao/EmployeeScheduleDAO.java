package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.EmployeeScheduleModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the ShiftModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class EmployeeScheduleDAO extends BaseBusinessDAO<EmployeeScheduleModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(EmployeeScheduleDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public EmployeeScheduleDAO(BusinessDAOContext context) {
        super(context, EmployeeScheduleModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}