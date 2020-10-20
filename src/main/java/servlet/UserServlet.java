package servlet;
import dto.UserDTO;
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

public class UserServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserServlet.class);
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtil su = new ServletUtil(req, resp);
        String action = req.getParameter(ServletConstants.ACTION);
        switch (action) {
            case ServletConstants.UPDATE_USER : actionUpdateUser(su); break;
            case ServletConstants.GET_USER_BY_ID : actionGetUserById(su); break;

            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void actionGetUserById(ServletUtil su) {
        try {
            Long userId = su.getSessionUserId();
            UserDelegate userDelegate = new UserDelegate();
            UserVO userVO = userDelegate.getUserById(userId);
            su.sendDTO(ServletConstants.STATUS_OK, userVO);
        } catch (Exception e) {
            log.error("SERVLET Cannot get user by id. actionGetUserById()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionUpdateUser(ServletUtil su) {
        try {
            UserDTO userDTO = su.deserializeDTO(UserDTO.class);
            UserVO userVO = userDTO;
            UserDelegate userDelegate = new UserDelegate();
            int upd = userDelegate.updateUser(userVO);
            su.sendDTO(ServletConstants.STATUS_OK, upd);
        } catch (Exception e) {
            log.error("SERVLET Cannot add update user. actionAddNewUser()", e);
            su.sendError(e.getMessage());
        }
    }

}