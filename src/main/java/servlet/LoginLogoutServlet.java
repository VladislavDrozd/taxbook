package servlet;

import logic.UserDelegate;
import org.apache.log4j.Logger;
import util.ServletUtil;
import vo.UserVO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class LoginLogoutServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginLogoutServlet.class);
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtil su = new ServletUtil(req, resp);
        String action = req.getParameter(ServletConstants.ACTION);
        switch (action) {
            case ServletConstants.LOGIN : actionLogin(su); break;
            case ServletConstants.LOGOUT : actionLogout(su); break;
            case ServletConstants.REGISTER : actionRegister(su); break;

            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void actionLogin(ServletUtil su) {
        try {
            String loginName = su.getRequest().getParameter("loginName");
            String password = su.getRequest().getParameter("password");
            //String language = su.getRequest().getParameter("language");
            String language = ServletConstants.LANGUAGE_UA; // now default

            UserDelegate userDelegate = new UserDelegate();
            Long userId = userDelegate.loginUser(loginName, password);

            if (userId == null) {
                log.info("Access denied for: login name = " + loginName + " , password = " + password);
                su.sendDTO(ServletConstants.STATUS_UNAUTHORIZED, "Wrong login name or password");
                return;
            }

            HttpSession session = su.getRequest().getSession(true);
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_USER_ID, userId);
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_LANGUAGE, language);

            log.info("User logged in: login name = " + loginName + " , password = " + password);
            su.getResponse().sendRedirect(ServletConstants.APP_LINK + "html/incomeBook.component.html");
        } catch (Exception e) {
            log.error("SERVLET LOGIN Cannot login session. actionLogin()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionLogout(ServletUtil su) {
        try {
            su.getRequest().getSession().invalidate();
        } catch (Exception e) {
            log.error("SERVLET LOGOUT Cannot logout session. actionLogout()", e);
            su.sendError(e.getMessage());
        }
    }

    private void actionRegister(ServletUtil su) {
        try {
            //UserDTO userDTO = su.deserializeDTO(UserDTO.class);
            //UserVO userVO = userDTO;
            UserVO userVO = new UserVO(1L,"TEST","email","3", "3", "password", new Date(), new Date(), 'Y');
            UserDelegate userDelegate = new UserDelegate();

            //String language = su.getRequest().getParameter("language");
            String language = ServletConstants.LANGUAGE_UA; // now default

            //check if new user`s email already exists in database
            boolean isEmailIsAlreadyExists = userDelegate.checkIfUserEmailIsAlreadyExists(userVO.getEmail());
            if (isEmailIsAlreadyExists) {
                su.sendDTO(ServletConstants.STATUS_OK, "Email " + userVO.getEmail() + " is already registered");
            } else {
                Long userId = userDelegate.addUser(userVO);
                HttpSession session = su.getRequest().getSession(true);
                session.setAttribute(ServletConstants.ATTRIBUTE_NAME_USER_ID, userId);
                session.setAttribute(ServletConstants.ATTRIBUTE_NAME_LANGUAGE, language);
                log.info("Register new user: name = " + userVO.getName() + ", email = " + userVO.getEmail() + " ");
                su.getResponse().sendRedirect(ServletConstants.APP_LINK + "html/incomeBook.component.html");
            }
        } catch (Exception e) {
            log.error("SERVLET Cannot add new user. actionRegister()", e);
            su.sendError(e.getMessage());
        }

    }

}
