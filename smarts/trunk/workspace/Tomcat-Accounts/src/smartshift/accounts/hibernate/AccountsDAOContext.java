package smartshift.accounts.hibernate;

import org.hibernate.Session;
import smartshift.common.hibernate.DAOContext;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.properties.AppConstants;

/**
 * @author fevans
 * the account DB context
 */
public class AccountsDAOContext implements DAOContext {
    
    private static AccountsDAOContext instance = null;
    
    private AccountsDAOContext() { }

    /**
     * see DAOContext.getSession()
     */
    @Override
    public Session getSession() {
        return HibernateFactory.getSession(AppConstants.DB_SCHEMA_ACCOUNTS);
    }

    /**
     * see DAOContext.getContextID()
     */
    @Override
    public Object getContextID() {
        return "ACCOUNTS";
    }

    /**
     * gets an instance of this object to use
     * @return the instance
     */
    public static AccountsDAOContext getInstance() {
        if(instance == null)
            instance = new AccountsDAOContext();
        return instance;
    }

}
