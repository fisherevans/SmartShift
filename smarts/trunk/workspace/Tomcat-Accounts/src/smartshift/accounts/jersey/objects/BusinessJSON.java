package smartshift.accounts.jersey.objects;

import smartshift.accounts.cache.bo.Business;
import com.google.gson.annotations.Expose;

@SuppressWarnings("javadoc")
public class BusinessJSON {
    @Expose
    public Integer id;

    @Expose
    public String name;

    @Expose
    public String server;

    @Expose
    public ImageJSON image;

    @Expose
    public AddressJSON address;

    @Expose
    public Integer employeeID;

    public BusinessJSON(Business business) {
        id = business.getID();
        name = business.getName();
        // TODO server        
    }
}
