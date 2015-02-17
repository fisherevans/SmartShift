package smartshift.accounts.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * address model
 */
@Entity
@Table(name = "Address")
public class AddressModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Expose
    @Column(name = "street1", length = 256)
    private String street1;

    @Expose
    @Column(name = "street2", length = 256)
    private String street2;

    @Expose
    @Column(name = "city", length = 50)
    private String city;

    @Expose
    @Column(name = "subDivision", length = 50)
    private String subDivision;

    @Expose
    @Column(name = "country", length = 50)
    private String country;

    @Expose
    @Column(name = "postalCode", length = 10)
    private String postalCode;

    @Expose
    @Column(name = "phoneNumber", length = 11)
    private String phoneNumber;
    
    /**
     * Initializes the object.
     */
    public AddressModel() {
        
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
     * @return the street1
     */
    public String getStreet1() {
        return street1;
    }

    /**
     * @param street1 the street1 to set
     */
    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    /**
     * @return the street2
     */
    public String getStreet2() {
        return street2;
    }

    /**
     * @param street2 the street2 to set
     */
    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the subDivision
     */
    public String getSubDivision() {
        return subDivision;
    }

    /**
     * @param subDivision the subDivision to set
     */
    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
