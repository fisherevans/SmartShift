package smartshift.business.util.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smartshift.common.util.log4j.SmartLogger;
import smartshift.common.util.servlets.SmartServletUtil;

public class GREStatusServlet extends HttpServlet {
    private static final SmartLogger logger = new SmartLogger(GREStatusServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print(SmartServletUtil.getHeadHTML("Group Role Employee Monitor"));
    }
    
    
}
