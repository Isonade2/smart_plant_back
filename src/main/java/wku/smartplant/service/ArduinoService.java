package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.NotificationType;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantHistory;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArduinoService {

    private final PlantRepository plantRepository;
    private final PlantHistoryRepository plantHistoryRepository;
    private final QuestService questService;
    private final NotificationService notificationService;

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

        createNotification(plantHistoryDTO, findPlant);

        log.info("{} uuid 식물 기록 성공", uuid);
        String msg;
        if (findPlant.getGiveWater()) {
            msg = "water";
            findPlant.changeGiveWater(false);
            boolean isLevelUp = findPlant.addExpAndIsLevelUp(20); //경험치를 추가 함과 레벨업을 했는지 확인
            if (isLevelUp)
                notificationService.createNotification(findPlant.getMember().getId(), findPlant.getName() + " 식물이 레벨 업을 하였습니다! 축하합니다!", "home", NotificationType.레벨업);

            Long memberId = findPlant.getMember().getId();
            questService.updateQuestProgress(memberId, 2L);
        } else {
            msg = "saved";
        }
        return msg;
    }

    private void createNotification(PlantHistoryDTO plantHistoryDTO, Plant findPlant) {
        if (plantHistoryDTO.getTemp() < 10)
            notificationService.createNotification(findPlant.getMember().getId(), "식물의 온도가 낮습니다.","history", NotificationType.온도);
        if (plantHistoryDTO.getSoilHumidity() < 300)
            notificationService.createNotification(findPlant.getMember().getId(), "식물의 토양습도가 낮습니다.","history", NotificationType.토양습도);
        if (plantHistoryDTO.getRemainingWater() < 800)
            notificationService.createNotification(findPlant.getMember().getId(), "남은 물통의 물양이 부족합니다.","history", NotificationType.남은물);
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

            boolean isLevelUp = findPlant.addExpAndIsLevelUp(20); //경험치를 추가 함과 레벨업을 했는지 확인
            if (isLevelUp)
                notificationService.createNotification(findPlant.getMember().getId(), findPlant.getName() + " 식물이 레벨 업을 하였습니다! 축하합니다!", "home", NotificationType.레벨업);

            Long memberId = findPlant.getMember().getId();
            questService.updateQuestProgress(memberId, 2L);
            return "water"; //아두이노에서 water 문자를 받으면 물을 줌
        } else {
            return "water state false";
        }
    }

}
