package smartshift.business.jersey.objects;

import com.google.gson.annotations.Expose;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.hibernate.model.RoleModel;

public class RoleJSON {
    @Expose
    public Integer id;
    
    @Expose
    public String name;
    
    public RoleJSON(RoleModel rm) {
        id = rm.getId();
        name = rm.getName();
    }
}
