package edu.bbte.idde.krim2244.service.other;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dataaccess.repository.CarDao;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoNotFoundException;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoOperationFailedException;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("!jpa")
public class CarServiceOther implements edu.bbte.idde.krim2244.service.CarService {
    @Autowired
    private final CarDao carDao;

    private static final Logger LOG = LoggerFactory.getLogger(CarServiceOther.class);

    @Override
    public Car addCar(Car car) throws ServiceException {
        LOG.info("Adding new car");
        try {
            return carDao.create(car);
        } catch (RepoOperationFailedException e) {
            LOG.error(e.getMessage());
            throw new ServiceException("Failed to add car.", e);
        }
    }

    @Override
    public Car findCarById(Long id) throws ServiceException {
        LOG.info("Finding car by id: {}", id);
        try {
            return carDao.findById(id); // van mar kivetel a findById-tol
        } catch (RepoNotFoundException e) {
            LOG.error(e.getMessage());
            throw new ServiceException("Car with ID " + id + " could not be found.", e);
        }
    }

    @Override
    public List<Car> listAllCars() {
        LOG.info("Listing all cars");
        return carDao.findAll();
    }

    @Override
    public List<Car> findByBrand(String brand) {
        LOG.info("Finding car by brand: {}", brand);
        return carDao.findByBrand(brand);
    }

    @Override
    public List<Car> findByYear(int year) {
        LOG.info("Finding car by year: {}", year);
        return carDao.findByYear(year);
    }

    @Override
    public Car modifyCar(Car car) throws ServiceException {
        LOG.info("Modifying car");
        try {
            return carDao.update(car);
        } catch (RepoNotFoundException e) {
            LOG.error(e.getMessage());
            throw new ServiceException("Car with ID " + car.getId() + " could not be found.");
        }
    }

    @Override
    public void removeCar(Long id) throws ServiceException {
        LOG.info("Removing car by id: {}", id);
        Car car = findCarById(id);
        if (car == null) {
            LOG.error("Car with ID {} could not be found.", id);
            throw new ServiceException("Car with ID " + id + " could not be found.");
        }
        try {
            carDao.delete(car);
        } catch (RepoNotFoundException e) {
            LOG.error(e.getMessage());
            throw new ServiceException("Car with ID " + id + " could not be found.");
        }
    }

    @Override
    public List<Car> findCarByBrandOrModel(String query) {
        return List.of();
    }
}