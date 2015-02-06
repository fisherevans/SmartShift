package smartshift.accounts.jersey.objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class UserFullJSON {
    @Expose
    public UserJSON user;

    @Expose
    public List<BusinessJSON> businesses = new ArrayList<>();
}
