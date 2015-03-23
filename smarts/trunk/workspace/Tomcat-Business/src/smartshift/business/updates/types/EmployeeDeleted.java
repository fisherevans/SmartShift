package smartshift.business.updates.types;

import java.util.Date;
import smartshift.business.cache.bo.Employee;
import smartshift.business.jersey.objects.EmployeeJSON;
import smartshift.business.updates.BaseUpdate;
import com.google.gson.annotations.Expose;

public class EmployeeDeleted extends BaseUpdate {
    public Employee deletedEmployee;

    public Date deleteTime;
    
    public EmployeeDeleted(Employee deletedEmployee, Employee executer) {
        super("employee", deletedEmployee.getID(), executer);
        this.deletedEmployee = deletedEmployee;
        deleteTime = new Date();
    }

    @Override
    public Object getJSONObject() {
        EmployeeDeletedJSON json = new EmployeeDeletedJSON();
        json.deletedEmployee = new EmployeeJSON(deletedEmployee);
        json.executer = new EmployeeJSON(getExecuter());
        json.deleteTime = deleteTime;
        return json;
    }
    
    public static class EmployeeDeletedJSON {
        @Expose
        public String type = "deleted";
        
        @Expose
        public EmployeeJSON deletedEmployee;
        
        @Expose
        public EmployeeJSON executer;
        
        @Expose
        public Date deleteTime;
    }
}
