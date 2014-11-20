package smartshift.common.hibernate.dao.accounts;

import org.hibernate.Session;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.properties.StaticProperties;

/**
 * Base data acess object for accounts
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class BaseAccountsDAO {
    /**
     * Gets the accounts DB session
     * @return the session
     */
    public static Session getAccountsSession() {
        return HibernateFactory.getSession(StaticProperties.DB_ACCOUNTS_SCHEMA);
    }
}
