package smartshift.business.jersey.objects;

import com.google.gson.annotations.Expose;

import smartshift.business.cache.bo.Role;
import smartshift.business.hibernate.model.GroupModel;
import smartshift.business.hibernate.model.RoleModel;
import smartshift.common.util.PrimativeUtils;

public class RoleJSON {
    @Expose
    public Integer id;
    
    @Expose
    public String name;
    
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
