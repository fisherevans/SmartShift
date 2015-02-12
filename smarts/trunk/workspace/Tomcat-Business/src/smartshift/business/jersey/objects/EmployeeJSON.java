package smartshift.business.jersey.objects;

import com.google.gson.annotations.Expose;
import smartshift.business.cache.bo.Employee;

public class EmployeeJSON {
    @Expose
    public Integer id;
    
    @Expose
    public String firstName;
    
    @Expose
    public String lastName;

    @Expose
    public Integer defaultGroupID;
    
    public EmployeeJSON(Employee e) {
        this.id = e.getID();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.defaultGroupID = e.getHomeGroup().getID();
    }
}