package smartshift.common.util;

import smartshift.common.util.log4j.SmartLogger;

public class ValidationUtil {
    private static final SmartLogger logger = new SmartLogger(ValidationUtil.class);
    
    public static String validateName(String name) {
        if(name == null)
            return null;
        name = name.trim().replaceAll(" +", " ");
        if(name.length() == 0)
            return null;
        return null;
    }
}
