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

    private Double temp; //온도
    private Double humidity; //습도
    private Double water;  // 물 용량
    private Double light; // 조도

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Builder
    public PlantHistory(Double temp, Double humidity, Double water, Double light, Plant plant) {
        this.temp = temp;
        this.humidity = humidity;
        this.water = water;
        this.light = light;
        this.plant = plant;
    }
}
