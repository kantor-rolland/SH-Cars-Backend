package edu.bbte.idde.krim2244.dataaccess.memory;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dataaccess.exception.RepoNotFoundException;
import edu.bbte.idde.krim2244.dataaccess.repository.CarDao;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CarDaoImpl implements CarDao {

    private final Map<Long, Car> carMap = new ConcurrentHashMap<>();
    private Long idCounter = 1L;

    @Override
    public void create(Car car) {
        car.setId(idCounter++);
        carMap.put(car.getId(), car);
    }

    @Override
    public void update(Car car) throws RepoNotFoundException {
        if (carMap.containsKey(car.getId())) {
            carMap.put(car.getId(), car);
        } else {
            throw new RepoNotFoundException("Car with ID " + car.getId() + "could not be found.");
        }
    }

    @Override
    public void delete(Car car) throws RepoNotFoundException {
        if (!carMap.containsKey(car.getId())) {
            throw new RepoNotFoundException("Car with ID " + car.getId() + " could not be found.");
        }
        carMap.remove(car.getId());
    }


    @Override
    public Car findById(Long id) throws RepoNotFoundException {
        return Optional.ofNullable(carMap.get(id))
                .orElseThrow(() -> new RepoNotFoundException("Car with ID " + id + " could not be found."));
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(carMap.values());
    }

    @Override
    public List<Car> findByBrand(String brand) {
        // filtering by brand
        List<Car> cars = new ArrayList<>();

        for (Car car : carMap.values()) {
            if (car.getBrand().equals(brand)) {
                cars.add(car);
            }
        }
        return cars;
    }

    @Override
    public List<Car> findByYear(int year) {
        // filtering by year
        List<Car> cars = new ArrayList<>();

        for (Car car : carMap.values()) {
            if (car.getYear() == year) {
                cars.add(car);
            }
        }
        return cars;
    }
}
