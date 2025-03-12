package edu.bbte.idde.krim2244.webapp.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.bbte.idde.krim2244.dataaccess.AbstractDaoFactory;
import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.service.CarService;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import edu.bbte.idde.krim2244.webapp.error.ErrorMessage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "CarAPIServlet", urlPatterns = {"/api/cars"})
public class CarAPIServlet extends HttpServlet {

    private static final CarService carService = new CarService(AbstractDaoFactory.getInstance().getCarDao());
    private static final Logger LOG = LoggerFactory.getLogger(CarAPIServlet.class);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        LOG.info("Initializing cars servlet...");
    }

    private void sendError(int status, String message, HttpServletResponse resp) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");

        // json formatum
        ErrorMessage errorMessage = new ErrorMessage(message);

        objectMapper.writeValue(resp.getOutputStream(), errorMessage);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET hivas, parameterek: " + req.getRequestURI());

        // jelezzük a HTTP válaszban, hogy JSON típusú kimenetet küldünk
        resp.setHeader("Content-Type", "application/json");

        // ha van query parameterunkben id, akkor csak az adott entitast teritjuk vissza
        String id = req.getParameter("id");
        if (id == null) {
            try {
                List<Car> cars = carService.listAllCars();

                LOG.info("Found cars: " + cars);
                objectMapper.writeValue(resp.getOutputStream(), cars);
            } catch (IOException e) {

                LOG.error(e.getMessage(), e);
                sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(), resp);
            }
        } else {
            try {
                Long idCar = Long.parseLong(id);
                Car car = carService.findCarById(idCar);
                if (car != null) {
                    LOG.info("Found car by id: " + car);
                    objectMapper.writeValue(resp.getOutputStream(), car);
                } else {
                    LOG.warn("Car not found for id: " + id);
                    sendError(HttpServletResponse.SC_NOT_FOUND, id, resp);
                }
            } catch (NumberFormatException e) {
                // longParse hiba
                LOG.error("Invalid car id format: " + id, e);
                sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), resp);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
                sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(), resp);
            } catch (ServiceException e) {
                LOG.error(e.getMessage(), e);
                sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Service error: " + e.getMessage(), resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Post hivas, parameterek: " + req.getRequestURI());

        // jelezzük a HTTP válaszban, hogy JSON típusú kimenetet küldünk
        resp.setHeader("Content-Type", "application/json");

        try {
            Car car = objectMapper.readValue(req.getInputStream(), Car.class);
            if (car.getUploadDate() == null) {
                car.setUploadDate(LocalDate.now());
            }

            LOG.info("Car: " + car);

            // int year primitiv tips
            if (car.getName() == null || car.getBrand() == null || car.getYear() == 0 || car.getPrice() == null) {
                LOG.info("Car doesn't have any data");
                sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car parameters", resp);
            }

            carService.addCar(car);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getOutputStream(), car);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), resp);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Service error: " + e.getMessage(), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Delete hivas, parameterek: " + req.getRequestURI());
        resp.setHeader("Content-Type", "application/json");

        // ha van query parameterunkben id akkor van mit torolni
        String id = req.getParameter("id");
        if (id == null) {
            LOG.info("Not found ID");
            sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not found ID", resp);
        } else {
            try {
                Long idCar = Long.parseLong(id);
                // Car car = carDao.findById(idCar);
                Car car = carService.findCarById(idCar);
                // ha van kocsi kitoroljuk
                if (car != null) {
                    carService.removeCar(car.getId());
                    LOG.info("Car with ID: " + car + " was deleted");
                    objectMapper.writeValue(resp.getOutputStream(), car);
                } else {
                    String message = "Car with ID: " + idCar + " was not found";
                    LOG.info(message);
                    sendError(HttpServletResponse.SC_NOT_FOUND, message, resp);
                }
            } catch (NumberFormatException e) { // ha nem szam
                String message = "Invalid car parameters";
                LOG.error(e.getMessage(), message);
                sendError(HttpServletResponse.SC_BAD_REQUEST, message, resp);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
                sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(), resp);
            } catch (ServiceException e) {
                LOG.error(e.getMessage(), e);
                sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Service error: " + e.getMessage(), resp);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Put hivas, parameterek: " + req.getRequestURI());

        // jelezzük a HTTP válaszban, hogy JSON típusú kimenetet küldünk
        resp.setHeader("Content-Type", "application/json");

        // eloszor meg kell nezni az ID-t
        String id = req.getParameter("id");

        if (id == null) {
            LOG.info("Not found ID");
            sendError(HttpServletResponse.SC_NOT_FOUND, "Not found ID", resp);
        }

        try {
            // ha van ID
            Long idCar = Long.parseLong(id);

            // megnezzuk hogy van e ilyen kocsi
            Car car = carService.findCarById(idCar);
            // ha van kocsi kitoroljuk
            if (car == null) {
                String message = "Car with ID: " + idCar + " was not found";
                LOG.info(message);
                sendError(HttpServletResponse.SC_NOT_FOUND, message, resp);
            } else {

                Car updatedCar = objectMapper.readValue(req.getInputStream(), Car.class);

                if (updatedCar.getUploadDate() == null) {
                    updatedCar.setUploadDate(LocalDate.now());
                }

                updatedCar.setId(car.getId());

                LOG.info("Car: " + updatedCar);
                carService.modifyCar(updatedCar);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(resp.getOutputStream(), car);
            }
        } catch (NumberFormatException e) {
            // longParse hiba
            LOG.error("Invalid car id format: " + id, e);
            sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), resp);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), resp);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Service error: " + e.getMessage(), resp);
        }
    }
}



