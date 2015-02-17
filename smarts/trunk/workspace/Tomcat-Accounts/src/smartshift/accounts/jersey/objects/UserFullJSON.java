package smartshift.accounts.jersey.objects;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.annotations.Expose;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * the json rep of the user object, includes what businesses are available
 */
public class UserFullJSON {
    /**
     * the json user
     */
    @Expose
    public UserJSON user;

    /**
     * the map of businesses available to the user
     */
    @Expose
    public Map<Integer, BusinessJSON> businesses = new HashMap<>();
}
