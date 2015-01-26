package smartshift.common.util.log4j;

import org.apache.log4j.Logger;

/** Used to log exception in separate output stream
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class ExceptionLogger {
    private static final Logger logger = Logger.getLogger(ExceptionLogger.class);
    
    private static Long lastExceptionID = -1L;
    
    protected static final int TRACE = 0;
    
    protected static final int DEBUG = 1;
    
    protected static final int INFO = 2;
    
    protected static final int WARN = 3;
    
    protected static final int ERROR = 4;
    
    protected static final int FATAL = 5;
    
    private static Long getNextExceptionID() {
        Long thisExceptionID = System.currentTimeMillis();
        if(thisExceptionID <= lastExceptionID)
            thisExceptionID = lastExceptionID + 1;
        lastExceptionID = thisExceptionID;
        return thisExceptionID;
    }
    
    /** Log a full stack trace in this log, return a reference id for debugging - alos logs on the parent
     * @param parentLogger the logger calling this
     * @param level the level to log at
     * @param t the throwable/exception to log
     * @return the reference exception id
     */
    protected static Long logException(Logger parentLogger, String baseParentLoggerMsg, int level, Throwable t) {
        Long exceptionID = -1L;
        String parentLogMessage = null, logMessage = null;
        if(t == null) {
            parentLogMessage = baseParentLoggerMsg + " >>> NULL EXCEPTION THROWN <<<";
        } else {
            exceptionID = getNextExceptionID();
            parentLogMessage = baseParentLoggerMsg + String.format(" >>> EXCEPTION THROWN #%d <<< %s:%s (See %s)",
                    exceptionID, t.getClass().getSimpleName(), t.getLocalizedMessage(), ExceptionLogger.class.getSimpleName());
            logMessage = String.format("Exception #%d (%s - %s)", exceptionID, parentLogger.getName(), baseParentLoggerMsg);
        }
        switch(level) {
            case TRACE:
                parentLogger.trace(parentLogMessage);
                if(logMessage != null) logger.trace(logMessage, t);
                break;
            case DEBUG:
                parentLogger.debug(parentLogMessage);
                if(logMessage != null) logger.debug(logMessage, t);
                break;
            case INFO:
                parentLogger.info(parentLogMessage);
                if(logMessage != null) logger.info(logMessage, t);
                break;
            case WARN:
                parentLogger.warn(parentLogMessage);
                if(logMessage != null) logger.warn(logMessage, t);
                break;
            case ERROR:
                parentLogger.error(parentLogMessage);
                if(logMessage != null) logger.error(logMessage, t);
                break;
            case FATAL:
                parentLogger.fatal(parentLogMessage);
                if(logMessage != null) logger.fatal(logMessage, t);
                break;
            default:
                parentLogger.error(parentLogMessage);
                if(logMessage != null) logger.error(logMessage, t);
                break;
        }
        return exceptionID;
    }
}
