package smartshift.accounts.hibernate.dao;

import smartshift.accounts.hibernate.model.ContactMethodModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The access methods for Contact methods
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class ContactMethodDAO extends BaseAccountsDAO<ContactMethodModel> {
    private static final SmartLogger logger = new SmartLogger(ContactMethodDAO.class);
    
    /**
     * Initializes the object.
     */
    public ContactMethodDAO() {
        super(ContactMethodModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
