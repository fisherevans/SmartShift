package smartshift.accounts.jersey.objects;

import smartshift.accounts.cache.bo.User;
import com.google.gson.annotations.Expose;

public class UserSelfResponse {
    @Expose
    public Integer id;
    
    @Expose
    public String username;
    
    @Expose
    public String email;

    public UserSelfResponse(User user) {
        id = user.getID();
        username = user.getUserName();
        email = user.getEmail();
    }
}
