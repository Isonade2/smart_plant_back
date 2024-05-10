package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantHistory;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArduinoService {

    private final PlantRepository plantRepository;
    private final PlantHistoryRepository plantHistoryRepository;

    @Transactional
    public String saveHistoryByArduino(String uuid, PlantHistoryDTO plantHistoryDTO) {
        Optional<Plant> findPlantOptional = plantRepository.findByUuid(uuid);

        if (findPlantOptional.isEmpty()) {
            log.error("{} uuid 식물을 찾을 수 없습니다.", uuid);
            return "Plant not found";
        }

        Plant findPlant = findPlantOptional.get();

        PlantHistory plantHistory = new PlantHistory(plantHistoryDTO, findPlant);

        plantHistoryRepository.save(plantHistory);

        log.info("{} uuid 식물 기록 성공", uuid);
        String msg;
        if (findPlant.getGiveWater()) {
            msg = "water";
            findPlant.changeGiveWater(false);
            findPlant.changeExp(findPlant.getExp() + 10);
        } else {
            msg = "saved";
        }
        return msg;
    }

    @Transactional
    public String checkWaterState(String uuid) {
        Optional<Plant> findPlantOptional = plantRepository.findByUuid(uuid);
        if (findPlantOptional.isEmpty()) {
            return "Plant not found";
        }

        Plant findPlant = findPlantOptional.get();

        if (findPlant.getGiveWater()) {
            findPlant.changeGiveWater(false);
            findPlant.changeExp(findPlant.getExp() + 10);
            return "water"; //아두이노에서 water 문자를 받으면 물을 줌
        } else {
            return "water state false";
        }
    }

}
