package ru.ilold.servlets;

import ru.ilold.authbeans.LoginBean;
import ru.ilold.managers.AuthentificateManager;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/secure/*")
public class AuthFilter implements Filter {

    @EJB
    private AuthentificateManager authentificateManager;

    @Inject
    private LoginBean loginBean;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if(!loginBean.getStatusMessage().isStatus()) {
            response.sendRedirect("/web/auth/login.xhtml");
            return;
        }
        String resource = request.getRequestURI();
        if(authentificateManager.isGranted(loginBean.getEmail(), resource)) {
            response.sendRedirect("/web/pages/main.xhtml");
            return;
        }
        chain.doFilter(req, resp);
    }
    public void init(FilterConfig config) throws ServletException {

    }
}
