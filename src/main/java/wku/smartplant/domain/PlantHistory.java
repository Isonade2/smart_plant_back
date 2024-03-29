package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
public class PlantHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "plantHistory_id")
    private Long id;

    private Double temp; //온도
    private Double humidity; //습도
    private Double water;  // 물 용량
    private Double light; // 조도

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;


}
