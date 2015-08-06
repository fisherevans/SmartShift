package smartshift.common.util.servlets;

import javax.servlet.http.HttpServlet;
import smartshift.common.util.properties.AppConstants;

/** util functions for admin utility servlets
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
abstract class SmartServletUtil {
    /**
     * Gets header for default admin servlet styling
     * @param title The title of the web page
     * @return the header html
     */
    public static String getHeadHTML(String title) {
        String html = "";
        html += "<html>";
        html += "  <head>";
        html += "   <title>" + title + "</title>";
        html += "   <link href='http://fonts.googleapis.com/css?family=Ubuntu:400,700' rel='stylesheet' type='text/css'>"; 
        html += "   <link rel='stylesheet' type='text/css' href='/" + AppConstants.CONTEXT_PATH + "/static/css/simple.css'>"; 
        html += "   <script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>";
        html += "  </head>";
        html += "  <body>";
        return html;
    }

    /**
     * Gets footer for default admin servlet styling
     * @return the footer html
     */
    public static String getFootHTML() {
        String html = "";
        html += "  </body>";
        html += "</html>";
        return html;
    }
}
