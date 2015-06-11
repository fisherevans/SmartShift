package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.ScheduleModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the ShiftModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class ScheduleDAO extends BaseBusinessDAO<ScheduleModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(ScheduleDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public ScheduleDAO(BusinessDAOContext context) {
        super(context, ScheduleModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}