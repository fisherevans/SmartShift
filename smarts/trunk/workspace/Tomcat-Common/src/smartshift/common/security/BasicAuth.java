package smartshift.common.security;

import javax.xml.bind.DatatypeConverter;

/**
 * Allow to encode/decode the authentication
 * 
 * @author fevans
 */
public class BasicAuth {
    /**
     * Decode the basic auth and convert it to array login/password
     * 
     * @param auth The string encoded authentication
     * @return The login (case 0), the password (case 1)
     */
    public static String[] decode(String auth) {
        auth = auth.replaceFirst("[B|b]asic ", "");
        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);
        if(decodedBytes == null || decodedBytes.length == 0) {
            return null;
        }
        return new String(decodedBytes).split(":", 2);
    }
}
