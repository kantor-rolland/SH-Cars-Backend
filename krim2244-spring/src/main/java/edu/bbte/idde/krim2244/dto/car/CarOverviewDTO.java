package edu.bbte.idde.krim2244.dto.car;

import lombok.Data;
import lombok.ToString;

/**
 * Egyszerű kimenő DTO
 * <p>
 * Nem tartalmaz hosszas stringeket, melyeket kollekcióban nem szeretnénk kiküldeni.
 * Kihagyható még: kapcsolódó entitás információ, stb.
 */
@Data
@ToString(callSuper = true)
public class CarOverviewDTO {
    private String name;
    private String brand;
    private int year;
    private Double price;
}

