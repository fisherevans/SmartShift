package smartshift.common.hibernate.dao.accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smartshift.common.cache.bo.accounts.ContactMethodBO;
import smartshift.common.hibernate.model.accounts.ContactMethodModel;
import smartshift.common.hibernate.model.accounts.UserModel;
import smartshift.common.hibernate.model.accounts.UserContactMethodModel;
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
    public static List<ContactMethodModel> getContactMethods() {
        return GenericHibernateUtil.list(getAccountsSession(), ContactMethodModel.class);
    }
    
    /**
     * get a contact method
     * @param id the id to lookup
     * @return the contact method
     */
    public static ContactMethodModel getContactMethod(Integer id) {
        return GenericHibernateUtil.unique(getAccountsSession(), ContactMethodModel.class, id);
    }
    
    /**
     * get a user's contact methods
     * @param user the user to look up
     * @return the map of contact method type id to contact method BO
     */
    public static Map<Integer, ContactMethodBO> getUserContactMethodMap(UserModel user) {
        Map<Integer, ContactMethodBO> methods = new HashMap<>();
        for(UserContactMethodModel ucm:user.getUserContactMethods()) {
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
    public static ContactMethodBO getUserContactMethod(UserModel user, ContactMethodModel cm) {
        return getUserContactMethod(user, cm.getId());
    }

    /**
     * get a user's contact method
     * @param user the user
     * @param contactMethodID the contact method id
     * @return the user's contact method
     */
    public static ContactMethodBO getUserContactMethod(UserModel user, Integer contactMethodID) {
        for(UserContactMethodModel ucm:user.getUserContactMethods())
            if(ucm.getContactMethod().getId().equals(contactMethodID))
                return new ContactMethodBO(ucm);
        return null;
    }
}
