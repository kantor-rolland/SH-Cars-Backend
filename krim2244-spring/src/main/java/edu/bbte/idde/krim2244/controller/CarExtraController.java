package edu.bbte.idde.krim2244.controller;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import edu.bbte.idde.krim2244.dto.car.CarExtraDetailsDTO;
import edu.bbte.idde.krim2244.dto.extra.ExtraDetailsDTO;
import edu.bbte.idde.krim2244.mapper.CarMapper;
import edu.bbte.idde.krim2244.mapper.ExtraMapper;
import edu.bbte.idde.krim2244.service.CarExtraService;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cars")
@Profile("jpa")
public class CarExtraController {
    // alapotlet: kulon kulon letrehozzuk az entitasokat, ezzel a controllerrel pedig
    //            csak osszekossuk oket - meg csinaljuk a kapcsolatot

    @Autowired
    private final CarExtraService carExtraService;
    @Autowired
    private final CarMapper carMapper;
    @Autowired
    private final ExtraMapper extraMapper;

    public CarExtraController(CarExtraService carExtraService, CarMapper carMapper, ExtraMapper extraMapper) {
        this.carExtraService = carExtraService;
        this.carMapper = carMapper;
        this.extraMapper = extraMapper;
    }

    // TODO: mapperek
    // most mukodik
    @GetMapping("/{carId}/extras")
    // public ResponseEntity<CarExtraDetailsDTO> handleGet(@PathVariable Long carId) throws ServiceException {
    public ResponseEntity<List<ExtraDetailsDTO>> handleGet(@PathVariable Long carId) throws ServiceException {
        Car car = carExtraService.getCarById(carId);
        List<Extra> extras = car.getExtras();

        // return ResponseEntity.ok(carMapper.toCarExtraDetailsDTO(car));
        return  ResponseEntity.ok(extraMapper.toExtraDetailsDTOList(extras));
    }

    // hozzadas - osszekotes
    @PostMapping("/{carId}/extras/{extraId}")
    // public ResponseEntity<CarExtraDetailsDTO>
    public ResponseEntity<List<ExtraDetailsDTO>>
        handlePost(@PathVariable Long carId, @PathVariable Long extraId) throws ServiceException {

        Car car = carExtraService.addExtraToCar(carId, extraId);
        List<Extra> extras = car.getExtras();
        //return ResponseEntity.ok(carMapper.toCarExtraDetailsDTO(car));
        return  ResponseEntity.ok(extraMapper.toExtraDetailsDTOList(extras));
    }

    @DeleteMapping("/{carId}/extras/{extraId}")
    public ResponseEntity<CarExtraDetailsDTO>
        handleDelete(@PathVariable Long carId, @PathVariable Long extraId) throws ServiceException {
        // kitoroljuk az adott id-ju extrat az autobol
        Car car = carExtraService.deleteExtraFromCar(carId, extraId);
        // return ResponseEntity.ok(car);
        return ResponseEntity.ok(carMapper.toCarExtraDetailsDTO(car));
    }

}
