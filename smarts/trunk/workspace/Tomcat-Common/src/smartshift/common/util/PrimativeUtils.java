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
}
