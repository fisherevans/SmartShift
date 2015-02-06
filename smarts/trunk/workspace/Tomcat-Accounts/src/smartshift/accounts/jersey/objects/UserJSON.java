package smartshift.accounts.jersey.objects;

import smartshift.accounts.cache.bo.User;
import com.google.gson.annotations.Expose;

public class UserJSON {
    @Expose
    public Integer id;

    @Expose
    public String username;

    @Expose
    public String email;

    public UserJSON(User user) {
        id = user.getID();
        username = user.getUserName();
        email = user.getEmail();
    }
}
