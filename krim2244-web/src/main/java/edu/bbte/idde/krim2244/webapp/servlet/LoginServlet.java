package edu.bbte.idde.krim2244.webapp.servlet;

import com.github.jknack.handlebars.Template;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    private static final String VALID_USERNAME = "user";
    private static final String VALID_PASSWORD = "pass";

    @Override
    public void init() {
        LOG.info("Initializing filter");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // naplózzuk az összes HTTP hívást
        // itt végezhetnénk bármilyen ellenőrzést
        LOG.info("GET LOGIN --- {} {}", req.getMethod(), req.getRequestURI());
        Template template = HandlebarsTemplateFactory.getTemplate("login");

        Map<String, Object> model = new ConcurrentHashMap<>();
        // Map<String, Object> model = new HashMap<>();

        template.apply(model, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("POST LOGIN --- {} {}", req.getMethod(), req.getRequestURI());
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {

            LOG.info("Correct username and password!");

            session.setAttribute("username", username);

            resp.sendRedirect(req.getContextPath() + "/cars");
        } else {
            LOG.info("Incorrect username and password!");
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}