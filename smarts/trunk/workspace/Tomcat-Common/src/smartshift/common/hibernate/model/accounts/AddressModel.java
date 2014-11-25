package smartshift.common.hibernate.model.accounts;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "Address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
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
    
    public AddressModel() {
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
