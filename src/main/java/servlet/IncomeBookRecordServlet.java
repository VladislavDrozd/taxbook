package servlet;
import logic.IncomeBookRecordDelegate;
import org.apache.log4j.Logger;
import util.ServletUtil;
import vo.IncomeBookRecordVO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IncomeBookRecordServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(IncomeBookRecordServlet.class);
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtil su = new ServletUtil(req, resp);
        String action = req.getParameter(ServletConstants.ACTION);
        switch (action) {
            case ServletConstants.ADD_RECORD : actionAddNewRecord(su); break;
            case ServletConstants.UPDATE_RECORD : actionUpdateRecord(su); break;
            case ServletConstants.DELETE_RECORD : actionDeleteRecord(su); break;

            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void actionAddNewRecord(ServletUtil su) {
        try {
            IncomeBookRecordVO incomeBookRecordVO = su.deserializeDTO(IncomeBookRecordVO.class);
            IncomeBookRecordDelegate incomeBookRecordDelegate = new IncomeBookRecordDelegate();
            Long newRecordId = incomeBookRecordDelegate.addRecord(incomeBookRecordVO);
            su.sendDTO(ServletConstants.STATUS_OK, newRecordId);
        } catch (Exception e) {
            log.error("SERVLET Cannot add new record. actionAddNewRecord()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionUpdateRecord(ServletUtil su) {
        try {
            IncomeBookRecordVO incomeBookRecordVO = su.deserializeDTO(IncomeBookRecordVO.class);
            IncomeBookRecordDelegate incomeBookRecordDelegate = new IncomeBookRecordDelegate();
            int upd = incomeBookRecordDelegate.updateRecord(incomeBookRecordVO);
            su.sendDTO(ServletConstants.STATUS_OK, upd);
        } catch (Exception e) {
            log.error("SERVLET Cannot update record. actionUpdateRecord()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionDeleteRecord(ServletUtil su) {
        try {
            Long userId = Long.parseLong(su.getRequest().getParameter("uId"));
            Long recordId = Long.parseLong(su.getRequest().getParameter("rId"));
            IncomeBookRecordDelegate incomeBookRecordDelegate = new IncomeBookRecordDelegate();
            int del = incomeBookRecordDelegate.deleteRecord(userId, recordId);
            su.sendDTO(ServletConstants.STATUS_OK, del);
        } catch (Exception e) {
            log.error("SERVLET Cannot delete record. actionDeleteRecord()", e);
            su.sendError(e.getMessage());
        }
    }

}