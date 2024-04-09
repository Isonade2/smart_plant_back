package wku.smartplant.dto.plant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wku.smartplant.domain.PlantHistory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlantHistoryDTO {
    private Long id;
    private Long plantId; //식물 id
    private Double temp; //온도
    private Double humidity; //습도
    private Double soilHumidity; //토양습도
    private Double water;  // 물 용량
    private Double light; // 조도

    public PlantHistoryDTO(PlantHistory plantHistory) {
        id = plantHistory.getId();
        plantId = plantHistory.getPlant().getId();
        temp = plantHistory.getTemp();
        humidity = plantHistory.getHumidity();
        soilHumidity = plantHistory.getSoilHumidity();
        water = plantHistory.getWater();
        light = plantHistory.getLight();
    }

}
