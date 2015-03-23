package smartshift.business.updates.types;

import java.util.Map;
import smartshift.business.cache.bo.Employee;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.updates.BaseUpdate;

public class EmployeeUpdate extends BaseUpdate {
    public Employee employee;
    
    public EmployeeUpdate(String subType, Employee employee, Employee executer) {
        super("employee", subType, employee.getID(), executer);
        this.employee = employee;
    }

    @Override
    public Map<String, Object> getJSONMap() {
        Map<String, Object> json = super.getJSONMap();
        json.put("employee", new EmployeeJSON(employee));
        return json;
    }
}
