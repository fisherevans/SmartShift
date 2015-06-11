package smartshift.accounts.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * address model
 */
@Entity
@Table(name = "NextID")
public class NextIDModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "nextID")
    private Integer nextID = 0;
    
    /**
     * Initializes the object.
     */
    public NextIDModel() {
        
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the next id name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the next id
     */
    public Integer getNextID() {
        return nextID;
    }

    /**
     * @param nextID
     */
    public void setNextID(Integer nextID) {
        this.nextID = nextID;
    }
}
