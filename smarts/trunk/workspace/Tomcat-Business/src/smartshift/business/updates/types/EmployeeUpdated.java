package smartshift.business.updates.types;

import java.util.Date;
import smartshift.business.cache.bo.Employee;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.updates.BaseUpdate;
import com.google.gson.annotations.Expose;

public class EmployeeUpdated extends BaseUpdate {
    public Employee updatedEmployee;

    public Date updateTime;
    
    public EmployeeUpdated(Employee updatedEmployee, Employee executer) {
        super("employee", updatedEmployee.getID(), executer);
        this.updatedEmployee = updatedEmployee;
        updateTime = new Date();
    }

    @Override
    public Object getJSONObject() {
        EmployeeUpdatedJSON json = new EmployeeUpdatedJSON();
        json.updatedEmployee = new EmployeeJSON(updatedEmployee);
        json.executer = new EmployeeJSON(getExecuter());
        json.updateTime = updateTime;
        return json;
    }
    
    public static class EmployeeUpdatedJSON {
        @Expose
        public String type = "updated";
        
        @Expose
        public EmployeeJSON updatedEmployee;
        
        @Expose
        public EmployeeJSON executer;
        
        @Expose
        public Date updateTime;
    }
}
