package smartshift.business.updates;

import java.util.Arrays;
import smartshift.common.util.log4j.SmartLogger;

public class MultiID {
    private static final SmartLogger logger = new SmartLogger(MultiID.class);
    
    private Object[] _ids;
    
    public MultiID(Object ... ids) {
        _ids = ids;
    }

    public Object[] getIDs() {
        return _ids;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_ids);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MultiID)
            return Arrays.equals(_ids, ((MultiID)obj)._ids);
        return false;
    }
    
    
}
