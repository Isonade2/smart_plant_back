package wku.smartplant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantHistory;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantHistoryService {

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
        findPlant.changeExp(findPlant.getExp() + 10);

        PlantHistory plantHistory = new PlantHistory(plantHistoryDTO, findPlant);

        plantHistoryRepository.save(plantHistory);

        log.info("{} uuid 식물 기록 성공", uuid);
        String msg;
        if (findPlant.getGiveWater()) {
            msg = "water";
            findPlant.changeGiveWater(false);
        } else {
            msg = "saved";
        }
        return msg;
    }

    public Page<PlantHistoryDTO> findAllHistoryByPlantId(Long memberId, Long plantId, Pageable pageable) {
        Page<PlantHistory> plantHistoryPage = plantHistoryRepository.findByPlantIdAndMemberId(plantId, memberId, pageable);
        return plantHistoryPage.map(PlantHistoryDTO::new);
    }

    public List<PlantHistoryDTO> findAllHistory( ) {
        List<PlantHistory> plantHistoryList = plantHistoryRepository.findAll();
        return plantHistoryList.stream().map(PlantHistoryDTO::new).collect(Collectors.toList());
    }
}
