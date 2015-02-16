package smartshift.accounts.hibernate.model.custom;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GetActiveSessionsModel {
    @Id 
    public final Integer id;
    
    @Column 
    public final String username;

    @Column 
    public final String sessionKey;

    @Column 
    public final Integer businessID;

    @Column 
    public final Integer employeeID;

    @Column 
    public final Date lastActivity;

    public GetActiveSessionsModel(Integer id, String username, String sessionKey, Integer businessID, Integer employeeID, Date lastActivity) {
        this.id = id;
        this.username = username;
        this.sessionKey = sessionKey;
        this.businessID = businessID;
        this.employeeID = employeeID;
        this.lastActivity = lastActivity;
    }
}
