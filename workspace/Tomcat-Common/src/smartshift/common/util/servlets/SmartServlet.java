package smartshift.common.util.servlets;

import javax.servlet.http.HttpServlet;
import smartshift.common.util.properties.AppConstants;

/** base servlet for admin utilities
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
public class SmartServlet extends HttpServlet {
    private static final long serialVersionUID = 2536987667576697899L;

    protected String getHeadHTML(String title) {
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
    
    protected String getFootHTML() {
        String html = "";
        html += "  </body>";
        html += "</html>";
        return html;
    }
}
