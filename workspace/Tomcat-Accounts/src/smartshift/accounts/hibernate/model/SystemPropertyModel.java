package smartshift.accounts.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "SystemProperty")
public class SystemPropertyModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "servID", nullable = false)
    private ServerModel server;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "propVal", length = 256)
    private String value;

    /**
     * Initializes the object.
     */
    public SystemPropertyModel() {
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
     * @return the server
     */
    public ServerModel getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(ServerModel server) {
        this.server = server;
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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
