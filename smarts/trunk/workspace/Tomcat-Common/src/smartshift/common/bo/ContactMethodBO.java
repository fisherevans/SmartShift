package smartshift.common.bo;

import com.google.gson.annotations.Expose;
import smartshift.common.hibernate.model.accounts.UserContactMethod;

/**
 * The business object for a contact method. Belongs to a user
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class ContactMethodBO {
    /**
     * contact method type ID
     */
    @Expose
    private Integer contactMethodId;

    /**
     * name for the contact method type
     */
    @Expose
    private String contactMethodName;

    /**
     * value of this instance
     */
    @Expose
    private String value;
    
    /**
     * Creates a contact method business object based on a USerContactMEthod data model
     * @param ucm the model to base thos bo one
     */
    public ContactMethodBO(UserContactMethod ucm) {
        this(ucm.getContactMethod().getId(), ucm.getContactMethod().getName(), ucm.getContactMethodValue());
    }
    
    /**
     * creates a contact method bo
     * @param contactMethodId the contact method id
     * @param contactMethodName the name for the method type
     * @param value the value of the contact method
     */
    public ContactMethodBO(Integer contactMethodId, String contactMethodName, String value) {
        this.contactMethodId = contactMethodId;
        this.contactMethodName = contactMethodName;
        this.value = value;
    }

    /**
     * @return the contactMethodId
     */
    public Integer getContactMethodId() {
        return contactMethodId;
    }

    /**
     * @param contactMethodId the contactMethodId to set
     */
    public void setContactMethodId(Integer contactMethodId) {
        this.contactMethodId = contactMethodId;
    }

    /**
     * @return the contactMethodName
     */
    public String getContactMethodName() {
        return contactMethodName;
    }

    /**
     * @param contactMethodName the contactMethodName to set
     */
    public void setContactMethodName(String contactMethodName) {
        this.contactMethodName = contactMethodName;
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
