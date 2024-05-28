package wku.smartplant.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantHistory;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.dto.plant.PlantDTO;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;
import wku.smartplant.service.AchievementService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flask")
public class FlaskController {

    private final PlantRepository plantRepository;
    private final PlantHistoryRepository plantHistoryRepository;
    private final AchievementService achievementService;
    @GetMapping("/plant/{plantId}")
    public ResponseEntity<ResponseDTO<Map<String, Object>>> getPlant(@PathVariable Long plantId, @RequestParam("memberId") Long memberId) {
        //이 기능이 전부라 서비스 계층으로 따로 분리하지 않고 컨트롤러에서 처리
        Plant findPlant = plantRepository.findByIdAndMemberId(plantId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("식물을 찾을 수 없습니다."));
        PlantDTO plantDTO = new PlantDTO(findPlant);

        PlantHistory findRecentPlantHistory = plantHistoryRepository.findTopByOrderByCreatedDateDesc()
                .orElseThrow(() -> new EntityNotFoundException("식물 기록을 찾을 수 없습니다."));
        PlantHistoryDTO plantHistoryDTO = new PlantHistoryDTO(findRecentPlantHistory);

        Map<String, Object> contents = new HashMap<>();
        contents.put("plant", plantDTO);
        contents.put("plantHistory", plantHistoryDTO);

        achievementService.updateMemberAchievement(memberId, 3L, 1);
        return ResponseEntityBuilder.build("식물과 식물 기록 정보입니다.", OK, contents);
    }

    @GetMapping("/quest")
    public ResponseEntity<ResponseDTO<?>> uploadQuest(){
        return ResponseEntityBuilder.build("사진 업로드 성공", OK);
    }

}
