package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wku.smartplant.dto.plant.PlantHistoryDTO;

@Entity
@RequiredArgsConstructor
@Getter
@Builder
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

    public PlantHistory(PlantHistoryDTO plantHistoryDTO, Plant plant) {
        this.memberId = plant.getMember().getId();
        this.temp = plantHistoryDTO.getTemp();
        this.humidity = plantHistoryDTO.getHumidity();
        this.water = plantHistoryDTO.getWater();
        this.light = plantHistoryDTO.getLight();
        this.plant = plant;
    }
}
