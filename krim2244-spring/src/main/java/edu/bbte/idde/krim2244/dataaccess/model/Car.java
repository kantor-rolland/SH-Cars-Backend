package edu.bbte.idde.krim2244.dataaccess.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Component
@Entity
@Table(name = "car")
public class Car extends BaseEntity {

    private String name;
    private String brand;
    private int year;
    private Double price;

    // uj mezok SPA projekthez
    private int mileage;
    private int cubicCapacity;
    private int carPower;
    private String fuelType;
    private String transmission;


    @Column(name = "uploadDate")
    private LocalDate uploadDate;

    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST)
    private List<Extra> extras = new ArrayList<>();

}