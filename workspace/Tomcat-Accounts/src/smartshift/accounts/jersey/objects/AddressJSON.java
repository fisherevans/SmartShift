package smartshift.accounts.jersey.objects;

import com.google.gson.annotations.Expose;

public class AddressJSON {
    @Expose
    public String street1;

    @Expose
    public String street2;

    @Expose
    public String city;

    @Expose
    public String subDivision;

    @Expose
    public String country;

    @Expose
    public String postalCode;

    @Expose
    public String phoneNumber;

    @Expose
    public String website;
}
