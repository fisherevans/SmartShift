package smartshift.common.util;

/** Various string utility functions
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class PrimativeUtils {
    /** Joins an array of objects into one string using .toString. Delim is the glue
     * @param arr the objects
     * @param delim the glue
     * @return the string
     */
    public static String joinArray(Object[] arr, String delim) {
        String result = "";
        int id = 0;
        for(Object ele:arr) {
            if(id++ > 0)
                result += delim;
            result += ele.toString();
        }
        return result;
    }
    
    /** Checks whether a needle is in a haystack. null == null is true
     * @param needle what youre looking for
     * @param haystack the array
     * @return true if found or null match
     */
    public static Boolean inArray(Object needle, Object[] haystack) {
        for(Object hay:haystack)
            if(equalsIncludeNull(needle, hay))
                return true;
        return false;
    }
    
    /** Check if either both == null, or a.equals(b)
     * @param a object a
     * @param b object b
     * @return true for equality
     */
    public static Boolean equalsIncludeNull(Object a, Object b) {
        if(a == null || b == null)
            return a == b;
        else
            return a.equals(b);
    }
}
