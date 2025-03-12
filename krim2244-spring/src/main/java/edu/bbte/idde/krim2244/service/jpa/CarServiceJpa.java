package edu.bbte.idde.krim2244.service.jpa;

import edu.bbte.idde.krim2244.dataaccess.exception.RepoOperationFailedException;
import edu.bbte.idde.krim2244.dataaccess.jpa.CarJPADao;
import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.service.CarService;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Profile("jpa")
@Service
public class CarServiceJpa implements CarService {

    private final CarJPADao carJPADao;

    public CarServiceJpa(CarJPADao carJPADao) {
        this.carJPADao = carJPADao;
    }

    @Override
    public Car addCar(Car car) throws ServiceException {
        log.info("Adding new car");

        if (car == null) {
            throw new ServiceException("Car cannot be null.");
        }

        try {
            return carJPADao.save(car);
        } catch (RepoOperationFailedException e) {
            log.error("Error while adding car: {}", e.getMessage(), e);
            throw new ServiceException("Error while adding car.", e);
        }
    }

    // model vagy marka szerint
    @Override
    public List<Car> findCarByBrandOrModel(String query) {
        log.info("Find car by query: {}", query);

        return carJPADao.findByBrandOrModel(query);
    }


    @Override
    public Car findCarById(Long id) throws ServiceException {
        log.info("Finding car by id {}", id);
        Optional<Car> car = carJPADao.findById(id);
        if (car.isPresent()) {
            log.info("Found car {}", car.get());
            return car.get();
        } else {
            log.info("Car with id {} not found", id);
            throw new ServiceException("Car not found with ID: " + id);
        }
    }

    @Override
    public List<Car> listAllCars() {
        log.info("Listing all cars");
        return carJPADao.findAll();
    }

    @Override
    public List<Car> findByBrand(String brand) {
        log.info("Finding cars by brand {}", brand);
        return carJPADao.findByBrand(brand);
    }

    @Override
    public List<Car> findByYear(int year) {
        log.info("Finding cars by year {}", year);
        return carJPADao.findByYear(year);
    }

    @Override
    public Car modifyCar(Car car) throws ServiceException {
        log.info("Modifying car {}", car);
        if (!carJPADao.existsById(car.getId())) {
            log.info("Car not found with ID {}", car.getId());
            throw new ServiceException("Cannot modify a non-existing car with ID: " + car.getId());
        }
        return carJPADao.save(car);
    }

    @Override
    public void removeCar(Long id) throws ServiceException {
        log.info("Removing car with ID {}", id);
        if (!carJPADao.existsById(id)) {
            log.error("Cannot remove car with ID: " + id);
            throw new ServiceException("Cannot remove a non-existing car with ID: " + id);
        }
        carJPADao.deleteById(id);
    }
}

