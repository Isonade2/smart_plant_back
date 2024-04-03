package wku.smartplant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantHistory;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantHistoryService {

    private final PlantRepository plantRepository;
    private final PlantHistoryRepository plantHistoryRepository;

    public String writeHistory(String uuid, PlantHistoryDTO plantHistoryDTO) {
        Optional<Plant> plantOptional = plantRepository.findByUuid(uuid);

        if (plantOptional.isEmpty()) {
            log.error("{} uuid 식물을 찾을 수 없습니다.", uuid);
            return "Plant not found";
        }

        Plant plant = plantOptional.get();

        PlantHistory plantHistory = PlantHistory.builder()
                .temp(plantHistoryDTO.getTemp())
                .humidity(plantHistoryDTO.getHumidity())
                .water(plantHistoryDTO.getWater())
                .light(plantHistoryDTO.getLight())
                .plant(plant)
                .build();

        plantHistoryRepository.save(plantHistory);

        log.info("{} uuid 식물 기록 성공", uuid);
        return "저장 성공";

    }
}
