package ru.ilold.servlets;

import ru.ilold.authbeans.LoginBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/pages/*")
public class IsLoggedFilter implements Filter {

    @Inject
    private LoginBean loginBean;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if(loginBean.getStatusMessage().isStatus()) {
            loginBean.setLogged("logged.xhtml");
            chain.doFilter(req,resp);
            return;
        }
        loginBean.setLogged("notlogged.xhtml");
        chain.doFilter(req, resp);
        return;

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
