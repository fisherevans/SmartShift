package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.ShiftModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the ShiftModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class ShiftDAO extends BaseBusinessDAO<ShiftModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(ShiftDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public ShiftDAO(BusinessDAOContext context) {
        super(context, ShiftModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}