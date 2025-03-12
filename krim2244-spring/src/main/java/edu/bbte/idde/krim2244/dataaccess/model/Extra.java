package edu.bbte.idde.krim2244.dataaccess.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Component
@Entity
@Table(name = "extras")
@ToString(exclude = "car")
public class Extra extends BaseEntity {
    // ugy gondoltam hogy itt ellehetne tarolni az autonak az extra felszereltsegeit:
    // ulesfutes, kormanyfutes, tolatokamera, vonatohorog stb
    // tobb a tobbhoz kapcsolat lenne az autokkal

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = true)
    @JsonIgnore
    private Car car;
}
