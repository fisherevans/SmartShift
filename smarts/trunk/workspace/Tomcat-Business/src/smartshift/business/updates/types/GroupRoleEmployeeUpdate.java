package smartshift.business.updates.types;

import java.util.Map;
import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.jersey.objects.GroupJSON;
import smartshift.business.jersey.objects.RoleJSON;
import smartshift.business.updates.BaseUpdate;

public class GroupRoleEmployeeUpdate extends BaseUpdate {
    public Group group;
    
    public Role role;
    
    public Employee employee;
    
    public GroupRoleEmployeeUpdate(String subType, Group group, Role role, Employee employee, Employee executer) {
        super("group-role-employee", subType, group.getID(), executer);
        this.group = group;
        this.role = role;
        this.employee = employee;
    }

    @Override
    public Map<String, Object> getJSONMap() {
        Map<String, Object> json = super.getJSONMap();
        json.put("group", new GroupJSON(group));
        json.put("role", new RoleJSON(role));
        json.put("employee", new EmployeeJSON(employee));
        return json;
    }
}
