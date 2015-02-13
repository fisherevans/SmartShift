package smartshift.business.jersey.objects;

import com.google.gson.annotations.Expose;
import smartshift.business.hibernate.model.GroupModel;

public class GroupJSON {
    @Expose
    public Integer id;
    
    @Expose
    public Integer parentGroupID;
    
    @Expose
    public String name;
    
    public GroupJSON(GroupModel gm) {
        id = gm.getId();
        parentGroupID = gm.getId();
        name = gm.getName();
    }
}
