package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class PlantHistory extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "plant_history_id")
    private Long id;
    
    private Long memberId; //유저 인증용

    private Double temp; //온도
    private Double humidity; //습도
    private Double water;  // 물 용량
    private Double light; // 조도

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Builder
    public PlantHistory(Long id, Long memberId, Double temp, Double humidity, Double water, Double light, Plant plant) {
        this.id = id;
        this.memberId = memberId;
        this.temp = temp;
        this.humidity = humidity;
        this.water = water;
        this.light = light;
        this.plant = plant;
    }
}
