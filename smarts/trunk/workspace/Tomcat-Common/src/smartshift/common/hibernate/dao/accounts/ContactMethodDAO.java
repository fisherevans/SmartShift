package smartshift.common.hibernate.dao.accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smartshift.common.bo.ContactMethodBO;
import smartshift.common.hibernate.model.accounts.ContactMethod;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.hibernate.model.accounts.UserContactMethod;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * The access methods for Contact methods
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class ContactMethodDAO extends BaseAccountsDAO {
    /**
     * get all contact method types
     * @return the list of contact methods
     */
    public static List<ContactMethod> getContactMethods() {
        return GenericHibernateUtil.list(getAccountsSession(), ContactMethod.class);
    }
    
    /**
     * get a contact method
     * @param id the id to lookup
     * @return the contact method
     */
    public static ContactMethod getContactMethod(Integer id) {
        return GenericHibernateUtil.unique(getAccountsSession(), ContactMethod.class, id);
    }
    
    /**
     * get a user's contact methods
     * @param user the user to look up
     * @return the map of contact method type id to contact method BO
     */
    public static Map<Integer, ContactMethodBO> getUserContactMethodMap(User user) {
        Map<Integer, ContactMethodBO> methods = new HashMap<>();
        for(UserContactMethod ucm:user.getUserContactMethods()) {
            methods.put(ucm.getContactMethod().getId(), new ContactMethodBO(ucm));
        }
        return methods;
    }
    
    /**
     * get a user's contact method
     * @param user the user
     * @param cm the contact method
     * @return the user's contact method
     */
    public static ContactMethodBO getUserContactMethod(User user, ContactMethod cm) {
        return getUserContactMethod(user, cm.getId());
    }

    /**
     * get a user's contact method
     * @param user the user
     * @param contactMethodID the contact method id
     * @return the user's contact method
     */
    public static ContactMethodBO getUserContactMethod(User user, Integer contactMethodID) {
        for(UserContactMethod ucm:user.getUserContactMethods())
            if(ucm.getContactMethod().getId().equals(contactMethodID))
                return new ContactMethodBO(ucm);
        return null;
    }
}
