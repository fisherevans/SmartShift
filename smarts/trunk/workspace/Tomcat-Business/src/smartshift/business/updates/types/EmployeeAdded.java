package smartshift.business.updates.types;

import java.util.Date;
import smartshift.business.cache.bo.Employee;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.updates.BaseUpdate;
import com.google.gson.annotations.Expose;

public class EmployeeAdded extends BaseUpdate {
    public Employee newEmployee;

    public Date addTime;
    
    public EmployeeAdded(Employee newEmployee, Employee executer) {
        super("employee", newEmployee.getID(), executer);
        this.newEmployee = newEmployee;
        addTime = new Date();
    }

    @Override
    public Object getJSONObject() {
        EmployeeAddedJSON json = new EmployeeAddedJSON();
        json.newEmployee = new EmployeeJSON(newEmployee);
        json.executer = new EmployeeJSON(getExecuter());
        json.addTime = addTime;
        return json;
    }
    
    public static class EmployeeAddedJSON {
        @Expose
        public String type = "added";
        
        @Expose
        public EmployeeJSON newEmployee;
        
        @Expose
        public EmployeeJSON executer;
        
        @Expose
        public Date addTime;
    }
}
