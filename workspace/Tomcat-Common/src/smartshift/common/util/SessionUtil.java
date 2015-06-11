package smartshift.common.util;

import java.security.SecureRandom;
import org.hibernate.criterion.Restrictions;
import smartshift.common.security.session.UserSession;
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
    public static String keyAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
    
    /**
     * Length of session keys
     */
    public static Integer keyLength = 255;
    
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
    
    public static String getDebugStr(String session) {
        int length = session.length();
        if(length < 8)
            return session.substring(0, length/2) + "...";
        String start = session.substring(0, Math.min(length, 4));
        String end = session.substring(length-4, length);
        return start + "..." + end;
    }
    
    /** TODO
     * @param args
     */
    public static void main (String[] args) {
        String a = "0123456789";
        String b = "123456";
        String c = "12";
        String d= "";
        System.out.println(getDebugStr(a));
        System.out.println(getDebugStr(b));
        System.out.println(getDebugStr(c));
        System.out.println(getDebugStr(d));
    }
}
