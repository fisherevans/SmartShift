package smartshift.common.util.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** Used as the default logger for smartshift apps. Logs thrown exception in a seperate logger 
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class SmartLoggerTake2 extends Logger {
    public SmartLoggerTake2(String name) {
        super(name);
    }

    @Override
    public void trace(Object message, Throwable t) {
        trace(ExceptionLoggerTake2.logException(this, message.toString(), Level.TRACE, t));
    }
    
    @Override
    public void debug(Object message, Throwable t) {
        debug(ExceptionLoggerTake2.logException(this, message.toString(), Level.DEBUG, t));
    }
    
    @Override
    public void error(Object message, Throwable t) {
        error(ExceptionLoggerTake2.logException(this, message.toString(), Level.ERROR, t));
    }
    
    @Override
    public void fatal(Object message, Throwable t) {
        fatal(ExceptionLoggerTake2.logException(this, message.toString(), Level.FATAL, t));
    }
    
    @Override
    public void info(Object message, Throwable t) {
        info(ExceptionLoggerTake2.logException(this, message.toString(), Level.INFO, t));
    }
    
    @Override
    public void warn(Object message, Throwable t) {
        warn(ExceptionLoggerTake2.logException(this, message.toString(), Level.WARN, t));
    }
}
