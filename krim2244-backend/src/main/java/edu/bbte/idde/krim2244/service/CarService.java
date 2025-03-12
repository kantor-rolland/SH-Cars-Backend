package edu.bbte.idde.krim2244.service;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dataaccess.repository.CarDao;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoNotFoundException;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoOperationFailedException;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CarService implements CarServiceInterface {
    private final CarDao carDao;
    private static final Logger LOG = LoggerFactory.getLogger(CarService.class);

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public void addCar(Car car) throws ServiceException {
        LOG.info("Adding new car");
        try {
            carDao.create(car);
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
    public void modifyCar(Car car) throws ServiceException {
        LOG.info("Modifying car");
        try {
            carDao.update(car);
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
}