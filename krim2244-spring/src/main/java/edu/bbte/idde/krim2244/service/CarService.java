package edu.bbte.idde.krim2244.service;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.service.exception.ServiceException;

import java.util.List;

public interface CarService {

    Car addCar(Car car) throws ServiceException;

    Car findCarById(Long id) throws ServiceException;

    List<Car> listAllCars();

    List<Car> findByBrand(String brand);

    List<Car> findByYear(int year);

    Car modifyCar(Car car) throws ServiceException;

    void removeCar(Long id) throws ServiceException;

    List<Car> findCarByBrandOrModel(String query);
}
