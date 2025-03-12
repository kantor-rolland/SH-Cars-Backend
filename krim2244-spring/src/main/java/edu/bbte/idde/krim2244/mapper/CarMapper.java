package edu.bbte.idde.krim2244.mapper;

import edu.bbte.idde.krim2244.dataaccess.model.Car;
import edu.bbte.idde.krim2244.dto.car.CarDetailsDTO;
import edu.bbte.idde.krim2244.dto.car.CarInDTO;
import edu.bbte.idde.krim2244.dto.car.CarOverviewDTO;
import edu.bbte.idde.krim2244.dto.car.CarExtraDetailsDTO;
import edu.bbte.idde.krim2244.dto.car.CarExtraOverviewDTO;
import org.mapstruct.Mapper;
// import org.mapstruct.factory.Mappers;
// import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Generált mapper osztály.
 * A mapstruct az alábbiak alapján generál megfelelő átalakító logikát.
 */
@Mapper(componentModel = "spring")
public abstract class CarMapper {

    // CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    // Peldakod alapjan
    public abstract List<CarOverviewDTO> toCarOverviewDTOList(List<Car> cars);

    public abstract CarOverviewDTO toCarOverviewDTO(Car car);

    public abstract List<CarDetailsDTO> toCarDetailsDTOList(List<Car> cars);

    public abstract CarDetailsDTO toCarDetailsDTO(Car car);

    // car in dto
    public abstract Car toCar(CarInDTO carInDTO);

    // car details
    public abstract Car detailstoCar(CarDetailsDTO carDetailsDTO);

    // ez folosleges
    public abstract List<Car> toCarList(List<CarInDTO> carInDTOs);

    // kibovitett amikben benne van az extra is
    public abstract CarExtraOverviewDTO toCarExtraOverviewDTO(Car car);

    // public abstract List<CarExtraOverviewDTO> toCarExtraOverviewDTOList(List<Car> cars);

    public abstract CarExtraDetailsDTO toCarExtraDetailsDTO(Car car);

}