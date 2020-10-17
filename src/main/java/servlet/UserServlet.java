package servlet;
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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtil su = new ServletUtil(req, resp);
        String param = req.getParameter(ServletConstants.ACTION);
        switch (param) {
            case ServletConstants.ADD_NEW_USER : actionAddNewUser(su); break;
            case ServletConstants.UPDATE_USER : actionUpdateUser(su); break;

            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + param);
        }
    }

    private void actionAddNewUser(ServletUtil su) {
        try {
            //UserDTO userDTO = su.deserializeDTO(UserDTO.class);
            //UserVO userVO = userDTO;
            UserVO userVO = new UserVO(1L,"TEST","2","3", "3", "testingHash", new Date(), new Date(), 'Y');
            UserDelegate userDelegate = new UserDelegate();
            Long newUserId = userDelegate.addUser(userVO);
            su.sendDTO(ServletConstants.STATUS_OK, newUserId);
        } catch (Exception e) {
            log.error("SERVLET Cannot add new user. actionAddNewUser()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionUpdateUser(ServletUtil su) {
        try {

        } catch (Exception e) {
            log.error("SERVLET Cannot add update user. actionAddNewUser()", e);
            su.sendError(e.getMessage());
        }
    }

}