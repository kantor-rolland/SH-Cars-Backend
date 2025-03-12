package edu.bbte.idde.krim2244.dataaccess.jpa;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// kell extendelni
@Repository
@Profile("jpa")
public interface CarJPADao extends JpaRepository<Car, Long> {

    // minden auto kereses
    List<Car> findByBrand(String brand);

    List<Car> findByYear(int year);

    // keresunk model(name) vagy marka alapjan
    @Query("SELECT c FROM Car c WHERE c.brand = :input OR c.name = :input")
    List<Car> findByBrandOrModel(String input);

}
