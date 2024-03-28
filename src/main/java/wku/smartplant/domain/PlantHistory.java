package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
public class PlantHistory {

    @Id
    @GeneratedValue
    @Column(name = "plantHistory_id")
    private Long id;

    private Double temperature;
    private Double humidity;
    private Double water;
    private Double light;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;


}
