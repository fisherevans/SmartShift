package smartshift.business.jersey.objects;

import java.util.Map;
import java.util.Set;
import com.google.gson.annotations.Expose;

@SuppressWarnings("javadoc")
public class FullCacheJSON {
    @Expose
    public Integer selfEmployeeID;

    @Expose
    public Map<Integer, EmployeeJSON> employees;

    @Expose
    public Map<Integer, GroupJSON> groups;

    @Expose
    public Map<Integer, RoleJSON> roles;

    @Expose
    public Map<Integer, Map<Integer, Set<Integer>>> groupRoleEmployeeIDs;
}
