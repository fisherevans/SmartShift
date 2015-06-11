package smartshift.business.util.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.servlets.SmartServlet;

public class GREStatusServlet extends SmartServlet {
    private static final SmartLogger logger = new SmartLogger(GREStatusServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print(getHeadHTML("Group Role Employee Monitor"));
    }
    
    
}
