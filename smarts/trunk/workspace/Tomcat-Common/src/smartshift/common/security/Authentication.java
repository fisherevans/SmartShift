package smartshift.common.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.mindrot.jbcrypt.BCrypt;
import smartshift.common.cache.bo.accounts.User;
import smartshift.common.hibernate.dao.accounts.UserDAO;
import smartshift.common.hibernate.model.accounts.UserModel;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Authentication methods mapping HTTP Basic to the WebUser table
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class Authentication {
    private static SmartLogger logger = new SmartLogger(Authentication.class);

    /**
     * @param username the username of the basic auth
     * @param password The password of the basic auth
     * @return the user if the password username and password matches a record,
     * null if not
     * @throws WebApplicationException If any error occurs
     */
    public static User checkAuth(String username, String password) throws WebApplicationException {
        User user = null;
        try {
            logger.debug("Fetching web user with the username of: " + username);
            User tempUser = UserDAO.getUserByUsername(username);
            if(tempUser != null) {
                if(BCrypt.checkpw(password, tempUser.getPassHash())) {
                    user = tempUser;
                }
            }
        } catch(Exception e) {
            logger.error("Failed to fetch User", e);
            throw new WebApplicationException(APIResultFactory.getResponse(Status.INTERNAL_SERVER_ERROR, null, null));
        }
        return user;
    }
}
