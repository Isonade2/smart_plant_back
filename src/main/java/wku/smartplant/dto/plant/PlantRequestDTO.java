package wku.smartplant.dto.plant;

import lombok.Builder;
import lombok.Data;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantType;


@Data
public class PlantRequestDTO {
    private Long memberId;
    private PlantType plantType;
    private String name;


    @Builder
    public PlantRequestDTO(PlantType plantType, String name) {
        this.plantType = plantType;
        this.name = name;
    }

    public Plant toEntity(Member member){
        return Plant.builder()
                .member(member)
                .name(name)
                .plantType(plantType)
                .build();
    }
}
