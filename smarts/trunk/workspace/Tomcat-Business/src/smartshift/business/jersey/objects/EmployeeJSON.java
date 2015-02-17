package smartshift.business.jersey.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.annotations.Expose;

import smartshift.business.cache.bo.Employee;
import smartshift.business.cache.bo.Group;
import smartshift.business.cache.bo.Role;

public class EmployeeJSON {
    @Expose
    public Integer id;
    
    @Expose
    public String firstName;
    
    @Expose
    public String lastName;

    @Expose
    public Integer defaultGroupID;

    @Expose
    public Map<Integer, Set<Integer>> groupRoles;
    
    public EmployeeJSON(Employee e) {
        this.id = e.getID();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.defaultGroupID = e.getHomeGroup().getID();
    }
    
    public static EmployeeJSON getSimple(Employee employee) {
        EmployeeJSON employeeJson = new EmployeeJSON(employee);
        return employeeJson;
    }
    
    public static EmployeeJSON getFull(Employee employee) {
        EmployeeJSON employeeJson = getSimple(employee);
        employeeJson.groupRoles = new HashMap<>();
        for(Group group:employee.getGroups()) {
            Set<Integer> roles = new HashSet<>();
            for(Role role:employee.getRoles(group))
            	roles.add(role.getID());
            employeeJson.groupRoles.put(group.getID(), roles);
        }
        return employeeJson;
    }
}
