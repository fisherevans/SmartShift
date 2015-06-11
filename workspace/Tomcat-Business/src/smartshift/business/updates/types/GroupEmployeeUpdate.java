package smartshift.business.updates.types;

import java.util.Map;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.updates.BaseUpdate;
import smartshift.business.updates.MultiID;

public class GroupEmployeeUpdate extends BaseUpdate {
    public Group group;
    
    public Employee employee;
    
    public GroupEmployeeUpdate(String subType, Group group, Employee employee) {
        super("group-employee", subType, new MultiID(group.getID(), employee.getID()), null);
        this.group = group;
        this.employee = employee;
    }

    @Override
    public Map<String, Object> getJSONMap() {
        Map<String, Object> json = super.getJSONMap();
        json.put("group", new GroupJSON(group));
        json.put("employee", new EmployeeJSON(employee));
        return json;
    }
}
