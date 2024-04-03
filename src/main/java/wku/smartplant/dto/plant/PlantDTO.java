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

    public PlantDTO(Plant plant) {
        id = plant.getId();
        name = plant.getName();
        exp = plant.getExp();
        plantType = plant.getPlantType();
    }
}
