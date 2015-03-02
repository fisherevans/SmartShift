package smartshift.business.jersey.objects;

import smartshift.common.util.log4j.SmartLogger;

public class GroupRequestJSON {
    private static final SmartLogger logger = new SmartLogger(GroupRequestJSON.class);
    
    private Integer id;

    private String name;
    
    private Integer parentGroupID;
    
    public GroupRequestJSON() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentGroupID() {
        return parentGroupID;
    }

    public void setParentGroupID(Integer parentGroupID) {
        this.parentGroupID = parentGroupID;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
