package smartshift.accounts.hibernate;

import org.hibernate.Session;
import smartshift.common.hibernate.DAOContext;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.util.properties.AppConstants;

public class AccountsDAOContext implements DAOContext {
    
    private static AccountsDAOContext instance = null;

    @Override
    public Session getSession() {
        return HibernateFactory.getSession(AppConstants.DB_SCHEMA_ACCOUNTS);
    }

    @Override
    public Object getContextID() {
        return "ACCOUNTS";
    }
    
    public static AccountsDAOContext getInstance() {
        if(instance == null)
            instance = new AccountsDAOContext();
        return instance;
    }

}
