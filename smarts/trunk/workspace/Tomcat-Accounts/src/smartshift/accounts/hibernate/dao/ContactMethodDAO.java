package smartshift.accounts.hibernate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smartshift.accounts.hibernate.model.ContactMethodModel;
import smartshift.accounts.hibernate.model.UserContactMethodModel;
import smartshift.accounts.hibernate.model.UserModel;
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
}
