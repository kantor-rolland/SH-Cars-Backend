package edu.bbte.idde.krim2244.webapp.filter;

import edu.bbte.idde.krim2244.service.CarService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@WebFilter("/cars")
public class LoginFilter extends HttpFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CarService.class);

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        LOG.info("Initializing LoginFilter");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // naplózzuk az összes HTTP hívást
        // itt végezhetnénk bármilyen ellenőrzést
        LOG.info("Authentication filter: {} {}", req.getMethod(), req.getRequestURI());

        HttpSession session = req.getSession(false);

        if (session == null) {
            res.sendRedirect("login");
            return;
        }

        String username = (String) session.getAttribute("username");
        if (username == null) {
            res.sendRedirect("login");
            return;
        }

        // mindig továbbengedjük a szűrőláncot
        chain.doFilter(req, res);
    }
}
