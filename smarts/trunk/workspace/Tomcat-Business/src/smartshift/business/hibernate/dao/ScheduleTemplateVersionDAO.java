package smartshift.business.hibernate.dao;

import org.hibernate.Query;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.ScheduleTemplateVersionModel;
import smartshift.common.hibernate.DBAction;
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
            DBAction action = new DBAction(getSession());
            try {
                Query query = prepareNamedQuery(action.session(), ScheduleTemplateVersionModel.GET_MAX_TEMPLATE_ID);
                Integer dbMaxId = (Integer) query.uniqueResult();
                id = Math.max(maxTemplateID, dbMaxId);
            } catch(Exception e) {
                action.rolback();
                throw e;
            }
            action.commit();
            maxTemplateID = id + 1;
        }
        return id;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}