package edu.bbte.idde.krim2244.controller;

import edu.bbte.idde.krim2244.dto.car.CarDetailsDTO;
import edu.bbte.idde.krim2244.dto.car.CarInDTO;
import edu.bbte.idde.krim2244.mapper.CarMapper;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.bbte.idde.krim2244.service.CarService;
import edu.bbte.idde.krim2244.dataaccess.model.Car;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private final CarService carService;

    @Autowired
    private final CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping
    public ResponseEntity<List<CarDetailsDTO>> handleGet() {
        log.info("Handling GET request - /cars");
        List<Car> cars = carService.listAllCars();
        return ResponseEntity.ok(carMapper.toCarDetailsDTOList(cars));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarDetailsDTO>> handleGetSearch(
            @RequestParam("query") String query
    ) {
        log.info("-----------------------------");
        log.info("Handling GET request - /cars/search - by model or brand");
        // List<Car> cars = carService.listAllCars();
        log.info("query: " + query);
        log.info("-----------------------------");
        List<Car> cars = carService.findCarByBrandOrModel(query);

        return ResponseEntity.ok(carMapper.toCarDetailsDTOList(cars));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<CarDetailsDTO>> handleGetYear(@PathVariable int year) {
        log.info("Handling GET request - /cars filtering by year");
        List<Car> cars = carService.findByYear(year);
        cars.forEach(car -> log.info("Car: {}", car));
        return ResponseEntity.ok(carMapper.toCarDetailsDTOList(cars));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<CarDetailsDTO>> handleGetBrand(@PathVariable String brand) {
        log.info("Handling GET request - /cars filtering by brand");
        List<Car> cars = carService.findByBrand(brand);
        return ResponseEntity.ok(carMapper.toCarDetailsDTOList(cars));
    }

    @PostMapping
    public ResponseEntity<CarDetailsDTO> handlePost(@RequestBody @Valid CarInDTO carInDTO) throws ServiceException {
        log.info("Handling POST request - /cars");

        Car car = carMapper.toCar(carInDTO);
        LocalDate currentDate = LocalDate.now();
        car.setUploadDate(currentDate);
        Car savedCar = carService.addCar(car);
        return ResponseEntity.ok(carMapper.toCarDetailsDTO(savedCar));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDetailsDTO> handleGetById(@PathVariable Long id) throws ServiceException {
        log.info("Handling GET request - /cars/{id}");

        Car car = carService.findCarById(id);
        return ResponseEntity.ok(carMapper.toCarDetailsDTO(car));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDetailsDTO>
        handlePut(@PathVariable Long id, @RequestBody @Valid CarDetailsDTO carDetailsDTO)
            throws ServiceException {
        log.info("Handling PATCH request - /cars/{id}");
        Car newCar = carMapper.detailstoCar(carDetailsDTO);
        newCar.setId(id);
        Car modifiedCar = carService.modifyCar(newCar);
        return ResponseEntity.ok(carMapper.toCarDetailsDTO(modifiedCar));
    }

    // /cars/id -> DELETE metodus
    @DeleteMapping("/{id}")
    public ResponseEntity<String> handleDelete(@PathVariable Long id) throws ServiceException {
        log.info("Handling DELETE request - /cars/{id}");

        carService.removeCar(id);
        return ResponseEntity.ok("Car deleted");
    }
}