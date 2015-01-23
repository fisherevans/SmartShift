package smartshift.common.hibernate.dao.business;

import org.hibernate.Session;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.properties.AppConstants;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class BaseBusinessDAO {
    /**
     * Gets the business DB session
     * @return the session
     */
    public static Session getBusinessSession() {
        return HibernateFactory.getSession(AppConstants.DB_SCHEMA_BUSINESS_BASE);
    }
}
