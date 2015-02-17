package smartshift.accounts.hibernate.dao;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.model.ServerModel;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * The data access object for the Server Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class ServerDAO extends BaseAccountsDAO {

    /**
     * Fetch all servers
     * @return The list of all servers
     */
    public static List<ServerModel> getServers() {
        return GenericHibernateUtil.list(getAccountsSession(), ServerModel.class);
    }
    
    /**
     * Get a server
     * @param id the server id to look up
     * @return The server - null if not found
     */
    public static ServerModel getServer(Integer id) {
        return GenericHibernateUtil.unique(getAccountsSession(), ServerModel.class, id);
    }
    
    /**
     * Get a server by hostname
     * @param hostname the server hostname to look up
     * @return The server - null if not found
     */
    public static ServerModel getServerByHostname(String hostname) {
        return GenericHibernateUtil.uniqueByCriterea(getAccountsSession(), ServerModel.class, Restrictions.eq("hostname", hostname));
    }
}
