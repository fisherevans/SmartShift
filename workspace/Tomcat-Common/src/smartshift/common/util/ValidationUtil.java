package smartshift.common.util;

import smartshift.common.util.log4j.SmartLogger;

public class ValidationUtil {
    private static final SmartLogger logger = new SmartLogger(ValidationUtil.class);
    
    public static String validateName(String name) {
        logger.debug("validateName() Validating _" + name + "_");
        if(name == null)
            return null;
        name = name.trim().replaceAll(" +", " ");
        logger.debug("validateName() Trimmed _" + name + "_");
        if(name.length() == 0)
            return null;
        return name;
    }
}
