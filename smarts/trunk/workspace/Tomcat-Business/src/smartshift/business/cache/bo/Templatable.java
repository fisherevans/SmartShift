package smartshift.business.cache.bo;

import smartshift.common.util.Identifiable;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

public interface Templatable extends Identifiable, Stored{
    public SmartLogger getLogger();
}
