package edu.bbte.idde.krim2244.dto.car;

import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarExtraDetailsDTO {
    private Long id;
    private String name;
    private String brand;
    private int year;
    private Double price;
    private LocalDate uploadDate;
    private List<Extra> extras;
}
