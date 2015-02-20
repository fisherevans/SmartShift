package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.ScheduleTemplateVersionModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the ShiftModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class ScheduleTemplateVersionDAO extends BaseBusinessDAO<ScheduleTemplateVersionModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(ScheduleTemplateVersionDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public ScheduleTemplateVersionDAO(BusinessDAOContext context) {
        super(context, ScheduleTemplateVersionModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}