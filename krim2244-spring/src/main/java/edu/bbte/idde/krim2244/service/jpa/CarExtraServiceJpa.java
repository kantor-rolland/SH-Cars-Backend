package edu.bbte.idde.krim2244.service.jpa;

import edu.bbte.idde.krim2244.dataaccess.jpa.CarJPADao;
import edu.bbte.idde.krim2244.dataaccess.jpa.ExtraJPADao;
import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import edu.bbte.idde.krim2244.service.CarExtraService;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Profile("jpa")
@Service
public class CarExtraServiceJpa implements CarExtraService {

    private final CarJPADao carJPADao;
    private final ExtraJPADao extraJPADao;

    public CarExtraServiceJpa(CarJPADao carJPADao, ExtraJPADao extraJPADao) {
        this.carJPADao = carJPADao;
        this.extraJPADao = extraJPADao;
    }

    @Override
    public Car getCarById(Long id) throws ServiceException {
        log.info("Get car (with extras) with id {}", id);

        return carJPADao.findById(id).orElseThrow(
                () -> new ServiceException("Car not found")
        );
    }

    // osszekotjuk az autot az extraval
    @Override
    public Car addExtraToCar(Long carId, Long extraId) throws ServiceException {
        log.info("Adding extra to Car");

        // megkeressuk mindket entitast, hogy letezik e
        Car car = carJPADao.findById(carId).orElseThrow(
                () -> new ServiceException("Car not found")
        );

        Extra extra = extraJPADao.findById(extraId).orElseThrow(
                () -> new ServiceException("Extra not found")
        );

        List<Extra> extras = car.getExtras(); //eddigi extrak
        extra.setCar(car);
        extras.add(extra);

        extraJPADao.save(extra);

        car.setExtras(extras);
        carJPADao.save(car); // lementjuk a kocsit

        return car;
    }

    @Override
    public Car deleteExtraFromCar(Long carId, Long extraId) throws ServiceException {
        log.info("Deleting extra to Car");
        // megkeressuk mindket entitast, hogy letezik e
        Car car = carJPADao.findById(carId).orElseThrow(
                () -> new ServiceException("Car not found")
        );

        Extra extra = extraJPADao.findById(extraId).orElseThrow(
                () -> new ServiceException("Extra not found")
        );

        if (car.getExtras().contains(extra)) {
            car.getExtras().remove(extra);
            extra.setCar(null);

            // entitasok mentese
            extraJPADao.save(extra);
            carJPADao.save(car);
        } else {
            throw new ServiceException("Extra is not associated with the given Car");
        }
        return car;
    }
}
