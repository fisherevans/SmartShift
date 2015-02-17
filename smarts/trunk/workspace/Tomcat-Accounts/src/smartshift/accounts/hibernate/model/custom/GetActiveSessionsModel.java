package smartshift.accounts.hibernate.model.custom;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GetActiveSessionsModel {
    @Id 
    public Integer id;
    
    @Column 
    public String username;

    @Column 
    public String sessionKey;

    @Column 
    public Integer businessID;

    @Column 
    public Integer employeeID;

    @Column 
    public Date lastActivity;

    public GetActiveSessionsModel(Integer id, String username, String sessionKey, Integer businessID, Integer employeeID, Date lastActivity) {
        this.id = id;
        this.username = username;
        this.sessionKey = sessionKey;
        this.businessID = businessID;
        this.employeeID = employeeID;
        this.lastActivity = lastActivity;
    }
    
    public GetActiveSessionsModel() {
    	
    }
}
