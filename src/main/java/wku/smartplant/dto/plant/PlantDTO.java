package wku.smartplant.dto.plant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantType;

@Data
@Builder
@AllArgsConstructor
public class PlantDTO {
    private Long id;
    private String name;
    private Long exp;
    private PlantType plantType;
    private String uuid; //테스트 때만 사용

    public PlantDTO(Plant plant) {
        id = plant.getId();
        name = plant.getName();
        exp = plant.getExp();
        plantType = plant.getPlantType();
        uuid = plant.getUuid(); //추후 삭제
    }
}
