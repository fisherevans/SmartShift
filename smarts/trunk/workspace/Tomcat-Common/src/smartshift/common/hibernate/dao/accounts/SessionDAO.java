package smartshift.common.hibernate.dao.accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smartshift.common.bo.ContactMethodBO;
import smartshift.common.hibernate.model.accounts.ContactMethod;
import smartshift.common.hibernate.model.accounts.Session;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.hibernate.model.accounts.UserBusinessEmployee;
import smartshift.common.hibernate.model.accounts.UserContactMethod;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * The access methods for Sessions
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class SessionDAO extends BaseAccountsDAO {
    /*
    public static Session createSession(UserBusinessEmployee ube) {
        Session session = null;
        try {
            session = new Session();
            session.setSessionKey(generateSessionKey(ube));
            session.setUserBusinessEmployee(ube);
            GenericHibernateUtil.save(getAccountsSession(), session);
        } catch(Exception e) {
            
        }
        return session;
    }
    
    private static String generateSessionKey(UserBusinessEmployee ube) {
        String key = "";
        do {
            key = "somekey" + (int)(Math.random()*325427);
        } while(getSession(ube, key) != null);
        return key;
    }
    */
}
