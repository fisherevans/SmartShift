package smartshift.accounts.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.model.ServerModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the Server Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class ServerDAO extends BaseAccountsDAO<ServerModel> {
    private static final SmartLogger logger = new SmartLogger(ServerDAO.class);
    
    /**
     * Initializes the object.
     */
    public ServerDAO() {
        super(ServerModel.class);
    }
    
    /**
     * Get a server by hostname
     * @param hostname the server hostname to look up
     * @return The server - null if not found
     */
    public ServerModel uniqueByHostname(String hostname) {
        ServerModel model = uniqueByCriteria(Restrictions.eq("hostname", hostname)).execute();
        return model;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
