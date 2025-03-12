package edu.bbte.idde.krim2244.dto.car;

import lombok.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDate;

/**
 * Kiterjesztett kimenő DTO.
 * Minden kimenő információ az entitásról.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
//@NoArgsConstructor
//@AllArgsConstructor
public class CarDetailsDTO {
    // orokolt mezo az id
    private Long id;
    private String name;
    private String brand;
    private int year;
    private Double price;
    private LocalDate uploadDate;

    // uj mezok SPA projekthez
    private int mileage;
    private int cubicCapacity;
    private int carPower;
    private String fuelType;
    private String transmission;
}

// TODO: SPA projekthez be lehetne hozni egy tenyleges oldal alapjan valos infokat: valto, loero, km stb...