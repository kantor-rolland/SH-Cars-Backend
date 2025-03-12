package edu.bbte.idde.krim2244.dto.car;

import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarExtraInDTO {
    private String name;
    private String brand;
    private int year;
    private Double price;
    private List<Extra> extras;
}
