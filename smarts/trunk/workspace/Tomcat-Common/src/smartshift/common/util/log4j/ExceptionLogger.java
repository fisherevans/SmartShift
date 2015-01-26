package smartshift.common.util.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** Used to log exception in separate output stream
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class ExceptionLogger {
    private static final Logger logger = Logger.getLogger(ExceptionLogger.class);
    
    private static Long lastExceptionID = -1L;
    
    private static Long getNextExceptionID() {
        Long thisExceptionID = System.currentTimeMillis();
        if(thisExceptionID <= lastExceptionID)
            thisExceptionID = lastExceptionID + 1;
        thisExceptionID %= 86400000L;
        lastExceptionID = thisExceptionID;
        return thisExceptionID;
    }
    
    /** Log a full stack trace in this log, return a reference id for debugging - alos logs on the parent
     * @param parentLogger the logger calling this
     * @param level the level to log at
     * @param t the throwable/exception to log
     */
    protected static void logException(Logger parentLogger, String baseParentLoggerMsg, Level level, Throwable t) {
        Long exceptionID = -1L;
        if((Logger.getRootLogger().getLevel() != null && level.isGreaterOrEqual(Logger.getRootLogger().getLevel()))
                || (logger.getLevel() != null) && level.isGreaterOrEqual(logger.getLevel())) {
            String parentLogMessage = null, logMessage = null;
            if(t == null) {
                parentLogMessage = baseParentLoggerMsg + " >>> NULL EXCEPTION THROWN <<<";
            } else {
                exceptionID = getNextExceptionID();
                parentLogMessage = baseParentLoggerMsg + String.format(" >>> EXCEPTION THROWN #%d <<< %s:%s (See %s)",
                        exceptionID, t.getClass().getSimpleName(), t.getLocalizedMessage(), ExceptionLogger.class.getSimpleName());
                logMessage = String.format("Exception #%d (%s - %s)", exceptionID, parentLogger.getName(), baseParentLoggerMsg);
            }
            parentLogger.log(level, parentLogMessage);
            logger.log(level, logMessage, t);
        }
    }
}
