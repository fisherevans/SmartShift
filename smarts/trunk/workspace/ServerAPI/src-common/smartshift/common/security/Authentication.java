package smartshift.common.security;

import javax.ws.rs.WebApplicationException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.dao.test.WebUserDAO;
import smartshift.common.hibernate.model.test.WebUser;
import smartshift.common.util.json.APIResultFactory;

/**
 * Authentication methods mapping HTTP Basic to the WebUser table
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class Authentication {
    private static Logger logger = Logger.getLogger(Authentication.class);

    /**
     * @param username the username of the basic auth
     * @param password The password of the basic auth
     * @return the user if the password username and password matches a record,
     * null if not
     * @throws WebApplicationException If any error occurs
     */
    public static WebUser checkAuth(String username, String password) throws WebApplicationException {
        WebUser user = null;
        try {
            Session session = HibernateFactory.getSession("smartshift");
            logger.debug("Fetching web user with the username of: " + username);
            WebUser tempUser = WebUserDAO.getWebUser(username, session);
            if(tempUser != null) {
                if(BCrypt.checkpw(password, tempUser.getPasswordHash())) {
                    user = tempUser;
                }
            }
            session.close();
        } catch(Exception e) {
            logger.error("Failed to fetch WebUser", e);
            throw APIResultFactory.getInternalErrorException();
        }
        return user;
    }
}
