package edu.bbte.idde.krim2244.dto.extra;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ExtraOverviewDTO {
    private Long id;
    private String name;
}
