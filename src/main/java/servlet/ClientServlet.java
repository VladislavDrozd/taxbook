package servlet;
import filter.ClientFilter;
import logic.ClientDelegate;
import org.apache.log4j.Logger;
import util.ServletUtil;
import vo.ClientVO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ClientServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ClientServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtil su = new ServletUtil(req, resp);
        String action = req.getParameter(ServletConstants.ACTION);
        switch (action) {
            case ServletConstants.ADD_CLIENT : actionAddClient(su); break;
            case ServletConstants.UPDATE_CLIENT : actionUpdateClient(su); break;
            case ServletConstants.GET_ALL_CLIENTS : actionGetAllClients(su); break;
            case ServletConstants.GET_CLIENTS_BY_FILTER : actionGetClientListByFilter(su); break;
            case ServletConstants.GET_CLIENT_BY_ID : actionGetClientById(su); break;
            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void actionAddClient(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            ClientVO clientVO = su.deserializeDTO(ClientVO.class);
            ClientDelegate clientDelegate = new ClientDelegate();
            Long newClientId = clientDelegate.addClient(userId, clientVO);
            su.sendDTO(ServletConstants.STATUS_OK, newClientId);
        } catch (Exception e) {
            log.error("SERVLET Cannot add new client. actionAddClient()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionUpdateClient(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            ClientVO clientVO = su.deserializeDTO(ClientVO.class);
            ClientDelegate clientDelegate = new ClientDelegate();
            int upd = clientDelegate.updateClient(userId, clientVO);
            su.sendDTO(ServletConstants.STATUS_OK, upd);
        } catch (Exception e) {
            log.error("SERVLET Cannot update client. actionUpdateClient()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionGetClientById(ServletUtil su) {
        try {
            Long clientId = Long.parseLong(su.getParameter("clientId"));
            ClientDelegate clientDelegate = new ClientDelegate();
            ClientVO clientVO = clientDelegate.getClientById(clientId);
            su.sendDTO(ServletConstants.STATUS_OK, clientVO);
        } catch (Exception e) {
            log.error("SERVLET Cannot get client by id. actionAddClient()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionGetAllClients(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            ClientDelegate clientDelegate = new ClientDelegate();
            List<ClientVO> clientVOList = clientDelegate.getAllClients(userId);
            su.sendDTOCollection(ServletConstants.STATUS_OK, clientVOList);
        } catch (Exception e) {
            log.error("SERVLET Cannot get all clients. actionGetAllClients()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionGetClientListByFilter(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            ClientFilter clientFilter = su.deserializeDTO(ClientFilter.class);
            ClientDelegate clientDelegate = new ClientDelegate();
            List<ClientVO> clientVOList = clientDelegate.getClientListByFilter(userId, clientFilter);
            su.sendDTOCollection(ServletConstants.STATUS_OK, clientVOList);
        } catch (Exception e) {
            log.error("SERVLET Cannot get all clients. actionGetClientListByFilter()", e);
            su.sendError(e.getMessage());
        }
    }

}