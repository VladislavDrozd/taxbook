package servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            // redirect to error unauthorised page
            httpServletResponse.sendError(ServletConstants.STATUS_UNAUTHORIZED);
        } else {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                // redirect to error unauthorised page
                httpServletResponse.sendError(ServletConstants.STATUS_UNAUTHORIZED);
            } else {
                chain.doFilter(request, response);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
