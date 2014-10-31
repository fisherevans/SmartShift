package smartshift.common.hibernate.model.accounts;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserContactMethodRelationData {
    // http://stackoverflow.com/questions/18975312/manytomany-with-additional-columns-using-elementcollection-and-java-util-map
    @Column(name="cMethodVal")
    private String value;

    public UserContactMethodRelationData() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
