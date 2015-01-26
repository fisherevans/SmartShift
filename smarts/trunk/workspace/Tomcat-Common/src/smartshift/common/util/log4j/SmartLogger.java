package smartshift.common.util.log4j;

import org.apache.log4j.Logger;

/** Used as the default logger for smartshift apps. Logs thrown exception in a seperate logger 
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class SmartLogger {
    private Logger logger;
    
    /** Wraps the standard logger to log thrown exceptions in a seperate log
     * @param clazz the class of the logger
     */
    public SmartLogger(Class<?> clazz) {
        logger = Logger.getLogger(clazz);
    }

    /**
     * @param message The log message
     * @param t the throwable
     * @see org.apache.log4j.Logger#trace(java.lang.Object, java.lang.Throwable)
     */
    public void trace(Object message, Throwable t) {
        ExceptionLogger.logException(logger, message.toString(), ExceptionLogger.TRACE, t);
    }
    
    /** 
     * @param message the log message
     * @see org.apache.log4j.Logger#trace(java.lang.Object)
     */
    public void trace(Object message) {
        logger.trace(message);
    }

    /**
     * @param message the log message
     * @param t the throwable
     * @see org.apache.log4j.Category#debug(java.lang.Object, java.lang.Throwable)
     */
    public void debug(Object message, Throwable t) {
        ExceptionLogger.logException(logger, message.toString(), ExceptionLogger.DEBUG, t);
    }
    
    /** 
     * @param message the log message
     * @see org.apache.log4j.Logger#debug(java.lang.Object)
     */
    public void debug(Object message) {
        logger.debug(message);
    }

    /**
     * @param message the log message
     * @param t the throwable
     * @see org.apache.log4j.Category#error(java.lang.Object, java.lang.Throwable)
     */
    public void error(Object message, Throwable t) {
        ExceptionLogger.logException(logger, message.toString(), ExceptionLogger.ERROR, t);
    }
    
    /** 
     * @param message the log message
     * @see org.apache.log4j.Logger#error(java.lang.Object)
     */
    public void error(Object message) {
        logger.error(message);
    }

    /**
     * @param message the log message
     * @param t the throwable
     * @see org.apache.log4j.Category#fatal(java.lang.Object, java.lang.Throwable)
     */
    public void fatal(Object message, Throwable t) {
        ExceptionLogger.logException(logger, message.toString(), ExceptionLogger.FATAL, t);
    }
    
    /** 
     * @param message the log message
     * @see org.apache.log4j.Logger#fatal(java.lang.Object)
     */
    public void fatal(Object message) {
        logger.fatal(message);
    }

    /**
     * @param message the log message
     * @param t the throwable
     * @see org.apache.log4j.Category#info(java.lang.Object, java.lang.Throwable)
     */
    public void info(Object message, Throwable t) {
        ExceptionLogger.logException(logger, message.toString(), ExceptionLogger.INFO, t);
    }
    
    /** 
     * @param message the log message
     * @see org.apache.log4j.Logger#info(java.lang.Object)
     */
    public void info(Object message) {
        logger.info(message);
    }

    /**
     * @param message the log message
     * @param t the throwable
     * @see org.apache.log4j.Category#warn(java.lang.Object, java.lang.Throwable)
     */
    public void warn(Object message, Throwable t) {
        ExceptionLogger.logException(logger, message.toString(), ExceptionLogger.WARN, t);
    }
    
    /** 
     * @param message the log message
     * @see org.apache.log4j.Logger#warn(java.lang.Object)
     */
    public void warn(Object message) {
        logger.warn(message);
    }
}
