package smartshift.accounts.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Business")
public class BusinessModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Expose
    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "description", nullable = true, length = 512)
    private String description;
    
    @Column(name = "groupID")
    private Integer groupID;

    @Column(name = "buildID")
    private Integer buildID;

    @Column(name = "servID", nullable = false)
    private Integer serverID;
    
    @Column(name = "imgID")
    private Integer imageID;

    @Column(name = "addressID")
    private Integer addressID;

    @Column(name = "inactive", nullable = false)
    private Boolean inactive = false;

    @Column(name = "flags", nullable = false)
    private Integer flags = 0;
    
    /**
     * Initializes the object.
     */
    public BusinessModel() {
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the groupID
     */
    public Integer getGroupID() {
        return groupID;
    }

    /**
     * @param groupID the groupID to set
     */
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    /**
     * @return the buildID
     */
    public Integer getBuildID() {
        return buildID;
    }

    /**
     * @param buildID the buildID to set
     */
    public void setBuildID(Integer buildID) {
        this.buildID = buildID;
    }

    /**
     * @return the serverID
     */
    public Integer getServerID() {
        return serverID;
    }

    /**
     * @param serverID the serverID to set
     */
    public void setServerID(Integer serverID) {
        this.serverID = serverID;
    }

    /**
     * @return the imageID
     */
    public Integer getImageID() {
        return imageID;
    }

    /**
     * @param imageID the imageID to set
     */
    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    /**
     * @return the addressID
     */
    public Integer getAddressID() {
        return addressID;
    }

    /**
     * @param addressID the addressID to set
     */
    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    /**
     * @return the inactive
     */
    public Boolean getInactive() {
        return inactive;
    }

    /**
     * @param inactive the inactive to set
     */
    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    /**
     * @return the flags
     */
    public Integer getFlags() {
        return flags;
    }

    /**
     * @param flags the flags to set
     */
    public void setFlags(Integer flags) {
        this.flags = flags;
    }
}
