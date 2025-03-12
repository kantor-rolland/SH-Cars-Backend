package edu.bbte.idde.krim2244.dataaccess.repository;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import java.util.List;

public interface CarDao extends Dao<Car> {

    List<Car> findByBrand(String brand);

    List<Car> findByYear(int year);
}