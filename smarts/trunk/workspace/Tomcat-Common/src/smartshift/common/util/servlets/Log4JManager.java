package smartshift.common.util.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.properties.AppConstants;

/**
 * servlet for configuration log4j levels
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class Log4JManager extends SmartServlet {
    private static final long serialVersionUID = -6080665311081379494L;
    
    private static SmartLogger thisLogger = new SmartLogger(Log4JManager.class);
    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print(getHeadHTML("Log4J Logger Configuration"));
        out.print("<script src='/" + AppConstants.CONTEXT_PATH + "/static/script/log4j.js'></script>");

        List<Level> levels = getLevels();
        Logger rootLogger = LogManager.getRootLogger();
        @SuppressWarnings("unchecked")
        Enumeration<Logger> catergories = LogManager.getCurrentLoggers();
        List<Logger> smartLoggers = new ArrayList<>(), otherLoggers = new ArrayList<>();
        while(catergories.hasMoreElements()) {
            Logger logger = catergories.nextElement();
            if(logger.getName().startsWith("smartshift"))
                smartLoggers.add(logger);
            else
                otherLoggers.add(logger);
        }
        Collections.sort(smartLoggers, new LoggerSorter());
        Collections.sort(otherLoggers, new LoggerSorter());
        
        out.println("<div class='content'><h1>Log4J Logger Configuration</h1>");
        out.println("<center><input type='text' id='logger-custom' />");
        out.println(getLevelSelect("custom", getLevels(), Level.DEBUG));
        out.println("<button onclick=\"setLogger('custom');\">SET Custom Logger</button></center><table>");
        
        int id = 1;
        
        out.println("<tr class='head'><td><b>Smart Loggers</b></td><td><b>Set Level</b></td></tr>");
        for(Logger logger:smartLoggers)
            out.print(printLogger(rootLogger, levels, logger, id++));
        
        out.println("<tr class='head'><td><b>Other Loggers</b></td><td><b>Set Level</b></td></tr>");
        for(Logger logger:otherLoggers)
            out.print(printLogger(rootLogger, levels, logger, id++));
        
        out.println("</table></div>");
        
        out.print(getFootHTML());
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String in = req.getReader().readLine();
        String[] input = in.split("\\|");
        if(input.length != 2) {
            thisLogger.warn("Invalid post request: " + in);
            resp.setStatus(400);
            return;
        }
        Logger logger = LogManager.getLogger(input[0]);
        Level level = Level.toLevel(input[1]);
        thisLogger.info("Setting Logger[" + input[0] + "] to Level[" + level.toString() + "]");
        logger.setLevel(level);
    }
    
    private String printLogger(Logger rootLogger, List<Level> levels, Logger logger, int id) {
        String html = "";
        Level level = logger.getLevel();
        if(level == null)
            level = rootLogger.getLevel();
        String loggerName = logger.getName();
        @SuppressWarnings("unused")
        String levelName = level.toString();
        html += String.format("<tr><td>%s</td><td>", logger.getName());
        html += getLevelSelect("" + id, levels, level);
        html += "&nbsp;&nbsp;&nbsp;<button onclick=\"setLogger('" + id + "');\">SET</button>";
        html += "<input id='logger-" + id + "' type='hidden' value='" + loggerName + "'/></td></tr>";
        return html;
    }
    
    private String getLevelSelect(String id, List<Level> levels, Level level) {
        String html = "<select id='level-" + id + "'>";
        for(Level availLevel:levels) {
            boolean selected = availLevel.toString().equals(level.toString());
            html += String.format("<option value='%s'%s>%s</option>", availLevel.toString(), selected ? " selected" : "", availLevel.toString());
        }
        html += "</select>";
        return html;
    }
        
    private List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();
        levels.add(Level.TRACE);
        levels.add(Level.DEBUG);
        levels.add(Level.INFO);
        levels.add(Level.WARN);
        levels.add(Level.ERROR);
        return levels;
    }
    
    /** sorts loggers
     * @author D. Fisher Evans <contact@fisherevans.com>
     */
    public static class LoggerSorter implements Comparator<Logger> {
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(Logger o1, Logger o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }
}
