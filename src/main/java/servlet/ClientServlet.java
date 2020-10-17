package servlet;
import logic.ClientDelegate;
import org.apache.log4j.Logger;
import util.ServletUtil;
import vo.ClientVO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ClientServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtil su = new ServletUtil(req, resp);
        String action = req.getParameter(ServletConstants.ACTION);
        switch (action) {
            case ServletConstants.ADD_CLIENT : actionAddClient(su); break;
            case ServletConstants.UPDATE_CLIENT : actionUpdateClient(su); break;
            case ServletConstants.GET_CLIENT_BY_ID : actionGetClientById(su); break;
            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void actionAddClient(ServletUtil su) {
        try {
            ClientVO clientVO = su.deserializeDTO(ClientVO.class);
            ClientDelegate clientDelegate = new ClientDelegate();
            Long newClientId = clientDelegate.addClient(clientVO);
            su.sendDTO(ServletConstants.STATUS_OK, newClientId);
        } catch (Exception e) {
            log.error("SERVLET Cannot add new client. actionAddClient()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionUpdateClient(ServletUtil su) {
        try {
            ClientVO clientVO = su.deserializeDTO(ClientVO.class);
            ClientDelegate clientDelegate = new ClientDelegate();
            int upd = clientDelegate.updateClient(clientVO);
            su.sendDTO(ServletConstants.STATUS_OK, upd);
        } catch (Exception e) {
            log.error("SERVLET Cannot update client. actionUpdateClient()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionGetClientById(ServletUtil su) {
        try {
            Long clientId = Long.parseLong(su.getRequest().getParameter("clientId"));
            ClientDelegate clientDelegate = new ClientDelegate();
            ClientVO clientVO = clientDelegate.getClientById(clientId);
            su.sendDTO(ServletConstants.STATUS_OK, clientVO);
        } catch (Exception e) {
            log.error("SERVLET Cannot add update client. actionAddClient()", e);
            su.sendError(e.getMessage());
        }
    }

}