package smartshift.accounts.jersey.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.annotations.Expose;

public class UserFullJSON {
    @Expose
    public UserJSON user;

    @Expose
    public Map<Integer, BusinessJSON> businesses = new HashMap<>();
}
