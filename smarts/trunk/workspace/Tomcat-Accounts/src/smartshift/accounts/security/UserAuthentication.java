package smartshift.accounts.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.mindrot.jbcrypt.BCrypt;
import smartshift.accounts.cache.bo.User;
import smartshift.accounts.hibernate.dao.UserDAO;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.log4j.SmartLogger;

/**
 * Authentication methods mapping HTTP Basic to the WebUser table
 * 
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserAuthentication {
    private static SmartLogger logger = new SmartLogger(UserAuthentication.class);

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
            User tempUser = User.load(username);
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
