package smartshift.business.updates.types;

import java.util.Map;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.updates.BaseUpdate;

public class GroupUpdate extends BaseUpdate {
    public Group group;
    
    public GroupUpdate(String subType, Group group, Employee executer) {
        super("group", subType, group.getID(), executer);
        this.group = group;
    }

    @Override
    public Map<String, Object> getJSONMap() {
        Map<String, Object> json = super.getJSONMap();
        json.put("group", new GroupJSON(group));
        return json;
    }
}
