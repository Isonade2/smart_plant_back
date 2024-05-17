package wku.smartplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/plant-history")
public class PlantHistoryController {

    private final PlantHistoryService plantHistoryService;

    @GetMapping("/{plantId}")
    @Operation(summary = "식물 기록 페이지 형식으로 반환",
            description = "member 토큰을 헤더로 보내야함. ?page=0 페이지 0부터 시작임, ?sort=temp,asc&page=0&size=10 으로 페이징 옵션 변경가능",
            responses = {
                    @ApiResponse(responseCode = "200", description = "페이지 리스트 반환 성공"),
                    @ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않거나 유저가 없을 경우")
            })
    public ResponseEntity<ResponseDTO<Page<PlantHistoryDTO>>> getAllByPlantId(@PathVariable Long plantId,
                                                                              @PageableDefault(size = 30, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Page<PlantHistoryDTO> pageHistory = plantHistoryService.getPlantHistoryByPlantIdAndMemberId(plantId, currentMemberId, pageable);
        return ResponseEntityBuilder.build(currentMemberId + " 회원의" + plantId +" 식물 기록입니다.", OK, pageHistory);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<PlantHistoryDTO>>> getAll(@PageableDefault(size = 30, sort = "createdDate", direction = Sort.Direction.DESC)
                                                     Pageable pageable) {
        Page<PlantHistoryDTO> allHistory = plantHistoryService.findAllHistory(pageable);
        return ResponseEntityBuilder.build("전체 식물 기록입니다.", OK, allHistory);
    }

}
