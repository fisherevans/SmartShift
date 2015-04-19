package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "GroupRoleCapability")
@IdClass(GroupRoleCapabilityID.class)
public class GroupRoleCapabilityModel {
    @Id
    @Column(name = "grpRoleID", nullable = false)
    private Integer groupRoleID;

    @Id
    @Column(name = "capID", nullable = false)
    private Integer capabilityID;

    public GroupRoleCapabilityModel() {
    }

    public Integer getGroupRoleID() {
        return groupRoleID;
    }

    public void setGroupRoleID(Integer groupRoleID) {
        this.groupRoleID = groupRoleID;
    }

    public Integer getCapabilityID() {
        return capabilityID;
    }

    public void setCapabilityID(Integer capabilityID) {
        this.capabilityID = capabilityID;
    }
    
    /** Overridden method - see parent javadoc
      * @see java.lang.Object#toString()
      */
    @Override
    public String toString() {
        String out = "";
        out += "groupRoleID:" + groupRoleID + ", ";
        out += "capabilityID:" + capabilityID;
        return out;
    }
}
