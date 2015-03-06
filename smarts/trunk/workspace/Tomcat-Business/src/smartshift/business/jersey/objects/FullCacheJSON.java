package smartshift.business.jersey.objects;

import java.util.Map;
import java.util.Set;
import smartshift.common.util.log4j.SmartLogger;
import com.google.gson.annotations.Expose;

public class FullCacheJSON {
    private static final SmartLogger logger = new SmartLogger(FullCacheJSON.class);
    
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
