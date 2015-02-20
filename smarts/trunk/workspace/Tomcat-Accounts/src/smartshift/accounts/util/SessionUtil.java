package smartshift.accounts.util;

import java.security.SecureRandom;
import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.SessionDAO;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * util for creating sessions
 */
public class SessionUtil {
    private static final SmartLogger logger = new SmartLogger(SessionUtil.class);
    
    /**
     * The characters to use in session keys
     */
    private static String keyAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
    
    /**
     * Length of session keys
     */
    private static Integer keyLength = 255;
    
    /**
     * Generate a new session key that is unique
     * @return a unique session key
     */
    public static String generateSessionKey() {
        logger.debug("generateSessionKey() Enter");
        String key = "";
        do {
            logger.debug("generateSessionKey() Generating key");
            key = getRandomSessionKey(keyLength);
        } while(AccountsDAOContext.dao(SessionDAO.class).list(Restrictions.eq("sessionKey", key)).size() > 0);
        return key;
    }
    
    /**
     * get random string using the key alphabet
     * @param length the length of the random string
     * @return the string
     */
    public static String getRandomSessionKey(int length) {
        logger.debug("getRandomSessionKey() Enter");
        SecureRandom random = new SecureRandom();
        StringBuffer buffer = new StringBuffer(keyAlphabet.length());
        for(int i = 0;i < keyAlphabet.length();i++) {
            buffer.append(keyAlphabet.charAt(random.nextInt(keyAlphabet.length())));
        }
        return buffer.toString();
    }

    /**
     * @return the current session alphebet
     */
    public static String getKeyAlphabet() {
        return keyAlphabet;
    }

    /**
     * @param keyAlphabet the new alphabet
     */
    public static void setKeyAlphabet(String keyAlphabet) {
        SessionUtil.keyAlphabet = keyAlphabet;
    }

    /**
     * @return the current sesion ke length
     */
    public static Integer getKeyLength() {
        return keyLength;
    }

    /**
     * @param keyLength the new session key length
     */
    public static void setKeyLength(Integer keyLength) {
        SessionUtil.keyLength = keyLength;
    }
}
