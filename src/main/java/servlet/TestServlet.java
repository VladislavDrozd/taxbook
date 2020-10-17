package servlet;
import logic.TestDelegate;
import logic.UserDelegate;
import org.apache.log4j.Logger;
import util.ServletUtil;
import vo.UserVO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;

public class TestServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(TestServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtil su = new ServletUtil(req, resp);
        String param = req.getParameter(ServletConstants.ACTION);
        switch (param) {
            //http://localhost:8080/taxbook/test?action=generateRecords&uId=1&q=10
            case ServletConstants.generateIncomeBookData : actionGenerateIncomeBookData(su); break;
            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + param);
        }
    }

   private void actionGenerateIncomeBookData(ServletUtil su) {
       try {
           TestDelegate testDelegate = new TestDelegate();
           Long userId = Long.parseLong(su.getRequest().getParameter("uId"));
           int quantity = Integer.parseInt(su.getRequest().getParameter("q"));
           testDelegate.generateIncomeBookData(userId, quantity);
       } catch (Exception e) {
           log.error("SERVLET TEST Cannot generate test income book data. handleGenerateIncomeBookData()", e);
           su.sendError(e.getMessage());
       }
   }

}