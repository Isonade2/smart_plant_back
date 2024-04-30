package wku.smartplant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.PlantHistoryService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plantHistory")
public class PlantHistoryController {

    private final PlantHistoryService plantHistoryService;

    @GetMapping("/{plantId}")
    public ResponseEntity<ResponseDTO<?>> getAllByPlantId(@PathVariable Long plantId, Pageable pageable) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Page<PlantHistoryDTO> allHistory = plantHistoryService.findAllHistoryByPlantId(currentMemberId, plantId, pageable);
        return ResponseEntityBuilder.build("식물 기록입니다.", OK, allHistory);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getAll(@PageableDefault(size = 30, sort = "createdDate", direction = Sort.Direction.DESC)
                                                     Pageable pageable) {
        Page<PlantHistoryDTO> allHistory = plantHistoryService.findAllHistory(pageable);
        return ResponseEntityBuilder.build("전체 식물 기록입니다.", OK, allHistory);
    }

}
