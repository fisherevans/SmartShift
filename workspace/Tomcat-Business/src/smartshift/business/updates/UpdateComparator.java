package smartshift.business.updates;

import java.util.Comparator;
import smartshift.common.util.log4j.SmartLogger;

public class UpdateComparator implements Comparator<BaseUpdate> {
    private static final SmartLogger logger = new SmartLogger(UpdateComparator.class);
    
    private static UpdateComparator instance = null;

    @Override
    public int compare(BaseUpdate a, BaseUpdate b) {
        return a.getTimestamp().compareTo(b.getTimestamp());
    }
    
    public static UpdateComparator instance() {
        if(instance == null)
            instance = new UpdateComparator();
        return instance;
    }
}
