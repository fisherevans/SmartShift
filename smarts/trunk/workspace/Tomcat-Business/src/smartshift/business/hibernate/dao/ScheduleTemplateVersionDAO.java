package smartshift.business.hibernate.dao;

import org.hibernate.Query;
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
    
    private static Integer maxTemplateID = -1;

    /**
     * @param context Base context for this business DAO
     */
    public ScheduleTemplateVersionDAO(BusinessDAOContext context) {
        super(context, ScheduleTemplateVersionModel.class);
    }
    
    /** gets the next available id. Accounts for those who may have not yet saved a id yet obtained by this method
     * @return the id to use next
     */
    public Integer getNextTemplateID() {
        Integer id = null;
        synchronized(maxTemplateID) {
            Query query = prepareNamedQuery(ScheduleTemplateVersionModel.GET_MAX_TEMPLATE_ID);
            Integer dbMaxId = (Integer) query.uniqueResult();
            id = Math.max(maxTemplateID, dbMaxId);
            maxTemplateID = id + 1;
        }
        return id;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}