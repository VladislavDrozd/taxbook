package servlet;
import dto.IncomeBookRecordDTO;
import filter.IncomeBookRecordFilter;
import logic.IncomeBookRecordDelegate;
import org.apache.log4j.Logger;
import util.ServletUtil;
import vo.IncomeBookRecordVO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            case ServletConstants.GET_ALL_RECORDS : actionGetAllRecords(su); break;
            case ServletConstants.GET_RECORDS_BY_FILTER : actionGetRecordsByFilter(su); break;

            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void actionAddNewRecord(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            IncomeBookRecordVO incomeBookRecordVO = su.deserializeDTO(IncomeBookRecordDTO.class);
            IncomeBookRecordDelegate incomeBookRecordDelegate = new IncomeBookRecordDelegate();
            Long newRecordId = incomeBookRecordDelegate.addRecord(userId, incomeBookRecordVO);
            su.sendDTO(ServletConstants.STATUS_OK, newRecordId);
        } catch (Exception e) {
            log.error("SERVLET Cannot add new record. actionAddNewRecord()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionUpdateRecord(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            IncomeBookRecordVO incomeBookRecordVO = su.deserializeDTO(IncomeBookRecordDTO.class);
            IncomeBookRecordDelegate incomeBookRecordDelegate = new IncomeBookRecordDelegate();
            int upd = incomeBookRecordDelegate.updateRecord(userId, incomeBookRecordVO);
            su.sendDTO(ServletConstants.STATUS_OK, upd);
        } catch (Exception e) {
            log.error("SERVLET Cannot update record. actionUpdateRecord()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionDeleteRecord(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            Long recordId = Long.parseLong(su.getParameter("recordId"));
            IncomeBookRecordDelegate incomeBookRecordDelegate = new IncomeBookRecordDelegate();
            int del = incomeBookRecordDelegate.deleteRecord(userId, recordId);
            su.sendDTO(ServletConstants.STATUS_OK, del);
        } catch (Exception e) {
            log.error("SERVLET Cannot delete record. actionDeleteRecord()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionGetAllRecords(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            IncomeBookRecordDelegate incomeBookRecordDelegate = new IncomeBookRecordDelegate();
            List<IncomeBookRecordDTO> incomeBookRecordDTOList =
                    incomeBookRecordDelegate.getAllRecords(userId);
            su.sendDTOCollection(ServletConstants.STATUS_OK, incomeBookRecordDTOList);
        } catch (Exception e) {
            log.error("SERVLET Cannot get all records. actionGetAllRecords()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionGetRecordsByFilter(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            IncomeBookRecordFilter filter = su.deserializeDTO(IncomeBookRecordFilter.class);
            IncomeBookRecordDelegate incomeBookRecordDelegate = new IncomeBookRecordDelegate();
            List<IncomeBookRecordDTO> incomeBookRecordDTOList =
                    incomeBookRecordDelegate.getRecordsByFilter(userId, filter);
            su.sendDTOCollection(ServletConstants.STATUS_OK, incomeBookRecordDTOList);
        } catch (Exception e) {
            log.error("SERVLET Cannot get records by filter. actionGetRecordsByFilter()", e);
            su.sendError(e.getMessage());
        }
    }

}