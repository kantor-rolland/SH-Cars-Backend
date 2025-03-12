package edu.bbte.idde.krim2244.service;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.service.exception.ServiceException;

public interface CarExtraService {

    // az osszetett/kibovitett DTO-kat hasznaljuk itt

    Car getCarById(Long id) throws ServiceException;

    // meglevo autohoz adunk meglevo extrat
    Car addExtraToCar(Long carId, Long extraId) throws ServiceException;

    // kivesuznk extrakat
    Car deleteExtraFromCar(Long carId, Long extraId) throws ServiceException;
}
