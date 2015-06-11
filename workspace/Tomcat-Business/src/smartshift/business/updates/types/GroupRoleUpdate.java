package smartshift.business.updates.types;

import java.util.Map;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.jersey.objects.RoleJSON;
import smartshift.business.updates.BaseUpdate;
import smartshift.business.updates.MultiID;

public class GroupRoleUpdate extends BaseUpdate {
    public Group group;
    
    public Role role;
    
    public GroupRoleUpdate(String subType, Group group, Role role) {
        super("group-role", subType,  new MultiID(group.getID(), role.getID()), null);
        this.group = group;
        this.role = role;
    }

    @Override
    public Map<String, Object> getJSONMap() {
        Map<String, Object> json = super.getJSONMap();
        json.put("group", new GroupJSON(group));
        json.put("role", new RoleJSON(role));
        return json;
    }
}
