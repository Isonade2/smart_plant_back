package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wku.smartplant.dto.plant.PlantHistoryDTO;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class PlantHistory extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "plant_history_id")
    private Long id;
    
    private Long memberId; //유저 인증용

    private Double temp; //온도
    private Double humidity; //습도
    private Double soilHumidity; //토양습도
    private Double remainingWater;  //남은 물 용량
    private Boolean gaveWater; //물을 줬는지
    private Double light; //조도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    public PlantHistory(PlantHistoryDTO plantHistoryDTO, Plant plant) {
        this.memberId = plant.getMember().getId();
        this.temp = plantHistoryDTO.getTemp();
        this.humidity = plantHistoryDTO.getHumidity();
        this.soilHumidity = plantHistoryDTO.getSoilHumidity();
        this.remainingWater = plantHistoryDTO.getRemainingWater();
        this.gaveWater = plantHistoryDTO.getGaveWater();
        this.light = plantHistoryDTO.getLight();
        this.plant = plant;
    }
}
