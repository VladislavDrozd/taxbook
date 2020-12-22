package servlet;

import logic.UserDelegate;
import org.apache.log4j.Logger;
import util.EmailUtil;
import util.ServletUtil;
import vo.UserVO;

import javax.mail.MessagingException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
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
            case ServletConstants.SEND_SIMPLE_EMAIL : actionSendSimpleEmail(su); break;

            default: su.sendDTO(ServletConstants.STATUS_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void actionLogin(ServletUtil su) {
        try {
            String name = su.getRequest().getParameter("login");
            String password = su.getRequest().getParameter("password");

            String languageParameter = su.getParameter(ServletConstants.COOKIE_NAME_LANGUAGE);
            String language = languageParameter != null ? languageParameter : ServletConstants.LANGUAGE_UA;

            UserDelegate userDelegate = new UserDelegate();
            Long userId = userDelegate.loginUser(name, password);

            if (userId == null) {
                log.info("Access denied for: login name = " + name + " , password = " + password);
                su.sendDTO(ServletConstants.STATUS_UNAUTHORIZED, "Wrong login name or password");
                return;
            }

            HttpSession session = su.getRequest().getSession(true);
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_USER_ID, userId);

            Cookie languageCookie = new Cookie(ServletConstants.COOKIE_NAME_LANGUAGE, language);
            languageCookie.setMaxAge(-1); // до конца сессии
            su.getResponse().addCookie(languageCookie);

            log.info("User logged in: login name = " + name + " , password = " + password);
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
            String name = su.getParameter("name");
            String email = su.getParameter("email");
            String phone = su.getParameter("phone");
            String taxGroup = su.getParameter("taxGroup");
            String password = su.getParameter("password");

            String languageParameter = su.getParameter(ServletConstants.COOKIE_NAME_LANGUAGE);
            String language = languageParameter != null ? languageParameter : ServletConstants.LANGUAGE_UA;


            UserVO userVO = new UserVO(null, name, email, phone,
                    taxGroup, password, new Date(), new Date(), 'Y');
            UserDelegate userDelegate = new UserDelegate();

            //check if new user`s email already exists in database
            boolean isEmailIsAlreadyExists = userDelegate.checkIfUserEmailIsAlreadyExists(userVO.getEmail());
            if (isEmailIsAlreadyExists) {
                //su.sendDTO(ServletConstants.STATUS_OK, "Email " + userVO.getEmail() + " is already registered");
                su.getResponse().setHeader("Content-Type", "text/html; charset=utf-8");
                PrintWriter pw = su.getResponse().getWriter();
                pw.println("<script type=\"text/javascript\">");
                pw.println("alert('На цей email вже є зареєстрований користувач.');");
                pw.println("window.location.href='"+ServletConstants.APP_LINK+"index.jsp';");
                pw.println("</script>");
            } else {
                Long userId = userDelegate.addUser(userVO);
                HttpSession session = su.getRequest().getSession(true);
                session.setAttribute(ServletConstants.ATTRIBUTE_NAME_USER_ID, userId);
                Cookie languageCookie = new Cookie(ServletConstants.COOKIE_NAME_LANGUAGE, language);
                languageCookie.setMaxAge(-1); // до конца сессии
                su.getResponse().addCookie(languageCookie);
                log.info("Register new user: name = " + userVO.getName() + ", email = " + userVO.getEmail() + " ");
                su.getResponse().sendRedirect(ServletConstants.APP_LINK + "html/incomeBook.component.html");
            }
        } catch (Exception e) {
            log.error("SERVLET Cannot add new user. actionRegister()", e);
            su.sendError(e.getMessage());
        }

    }

    private void actionSendSimpleEmail(ServletUtil su) {
        EmailUtil emailUtil = new EmailUtil();
        String ename = null;
        String eemail = null;
        String etext = null;
        try {
            ename = su.getRequest().getParameter("ename");
            eemail = su.getRequest().getParameter("eemail");
            etext = su.getRequest().getParameter("etext");
            emailUtil.sendSimpleMail("Taxbook FROM " + ename + ", " + eemail, etext);
        } catch (Exception e) {
            log.error("SERVLET Cannot send email. actionSendSimpleEmail()\n" + ename + " " + eemail + " " + etext );
            su.sendError("Cannot send email");
        }
    }

}