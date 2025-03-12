package edu.bbte.idde.krim2244.webapp.servlet;

import edu.bbte.idde.krim2244.dataaccess.AbstractDaoFactory;
import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.service.CarService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.github.jknack.handlebars.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/cars")
public class CarServlet extends HttpServlet {
    private static final CarService carService = new CarService(AbstractDaoFactory.getInstance().getCarDao());
    private static final Logger LOG = LoggerFactory.getLogger(CarServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        LOG.info("Initializing cars servlet...");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET hivas, parameterek: " + req.getRequestURI());

        List<Car> cars = carService.listAllCars();

        Map<String, Object> model = new ConcurrentHashMap<>();
        model.put("cars", cars);

        Template template = HandlebarsTemplateFactory.getTemplate("index");
        template.apply(model, resp.getWriter());
    }
}
