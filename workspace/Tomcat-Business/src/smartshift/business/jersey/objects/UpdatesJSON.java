package smartshift.business.jersey.objects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import smartshift.business.updates.BaseUpdate;
import com.google.gson.annotations.Expose;

public class UpdatesJSON {
    @Expose
    public Map<String, List<Object>> updates = new HashMap<>();
    
    public void addUpdate(BaseUpdate update) {
        getUpdates(update.getType()).add(update.getJSONMap());
    }
    
    public List<Object> getUpdates(String type) {
        List<Object> typeUpdates = updates.get(type);
        if(typeUpdates == null) {
            typeUpdates = new LinkedList<>();
            updates.put(type, typeUpdates);
        }
        return typeUpdates;
    }
}
