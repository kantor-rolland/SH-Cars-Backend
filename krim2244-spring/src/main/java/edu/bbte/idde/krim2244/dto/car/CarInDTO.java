package edu.bbte.idde.krim2244.dto.car;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CarInDTO {
    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String brand;

    @Positive
    @Range(min = 0, max = 2025)
    private int year;

    @Positive
    private Double price;

    // uj mezok SPA projekthez
    @Positive
    @Range(min = 0, max = 1000000) // 1 millio
    private int mileage;

    @Positive
    @Range(min = 0, max = 10000)
    private int cubicCapacity;

    @Positive
    @Range(min = 0, max = 10000)
    private int carPower;

    @NotEmpty
    @Size(max = 50)
    private String fuelType;

    @NotEmpty
    @Size(max = 50)
    private String transmission;
}
