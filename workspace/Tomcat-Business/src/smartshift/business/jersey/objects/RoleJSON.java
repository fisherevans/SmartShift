package smartshift.business.jersey.objects;

import smartshift.business.cache.bo.Role;
import smartshift.common.util.PrimativeUtils;
import com.google.gson.annotations.Expose;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * the json rep of a role
 */
public class RoleJSON {
    /**
     * the role id
     */
    @Expose
    public Integer id;
    
    /**
     * the role name
     */
    @Expose
    public String name;
    
    /**
     * Initializes the object.
     * @param r th ebase role
     */
    public RoleJSON(Role r) {
        id = r.getID();
        name = r.getName();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return name.hashCode() * id.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RoleJSON) {
            RoleJSON other = (RoleJSON) obj;
            return id == other.id && PrimativeUtils.equalsIncludeNull(name, other.name);
        } else
            return false;
    }
}
