package smartshift.accounts.jersey.objects;

import java.util.ArrayList;
import java.util.List;
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
    public List<BusinessJSON> businesses = new ArrayList<>();
}
