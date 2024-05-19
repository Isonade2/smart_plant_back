package wku.smartplant.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.websocket.OnError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.domain.QuestProgress;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.plant.PlantDTO;
import wku.smartplant.dto.plant.PlantRequestDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.AuthenticationSevice;
import wku.smartplant.service.PlantService;
import wku.smartplant.service.QuestService;


import java.util.List;

import static wku.smartplant.dto.ResponseEntityBuilder.*;

@RestController
@RequestMapping("/plant")
@RequiredArgsConstructor
@Slf4j
public class PlantController {

    private final QuestService questService;
    private final PlantService plantService;
    private final AuthenticationSevice authenticationSevice;

    // 클라이언트의 식물 정보 조회
    @GetMapping
    @Operation(summary = "식물 정보 조회",
            description = "사용자가 가지고 있는 식물들을 조회함")
    public ResponseEntity<ResponseDTO<List<PlantDTO>>> getMemberPlantList() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return build(currentMemberId + " 멤버의 식물 리스트", HttpStatus.OK, plantService.getAllPlantsByMemberId(currentMemberId));
    }

    @GetMapping("{plantId}")
    @Operation(summary = "특정 식물 조회",
            description = "사용자의 식물 중 특정 식물을 조회함")
    public ResponseEntity<ResponseDTO<PlantDTO>> getPlant(@PathVariable Long plantId) {
        PlantDTO plantDTO = plantService.findPlantById(plantId);
        return build("식물 조회 성공", HttpStatus.OK, plantDTO);
    }

//    @PostMapping("/join")
//    public ResponseEntity<ResponseDTO<Long>> join(PlantRequestDTO plantRequestDTO) {
//        log.info("join");
//        log.info(plantRequestDTO.toString());
//        Long currentMemberId = SecurityUtil.getCurrentMemberId();
//
//        Long plantid = plantService.createPlant(plantRequestDTO, currentMemberId);
//
//
//        return build("식물 등록 성공", HttpStatus.OK, plantid);
////        try{
////            Long id = authenticationSevice.isLoggedIn();
////        } catch (Exception e){
////            return build(e.getMessage(), HttpStatus.UNAUTHORIZED);
////        }
//    }

    @GetMapping("/{plantId}/water")
    @Operation(summary = "물 공급",
            description = "해당 식물에 물을 공급함 (토큰 필요)")
    public ResponseEntity<ResponseDTO<Boolean>> changeWaterState(@PathVariable Long plantId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        if (currentMemberId == 1L) {
            Boolean changedState = plantService.changeGiveWater(1L, plantId); //바뀐 상태
            String msg = "바뀐 상태는 " + changedState + "입니다.";
            return build(msg, HttpStatus.OK, changedState);
        }
        else{
            questService.updateQuestProgress(currentMemberId, 2L);
            return build("물 공급 완료", HttpStatus.OK, true);
        }

    }


    @Operation(summary = "대표식물 설정",
            description = "해당 식물을 대표 식물로 설정")
    @GetMapping("/{plantId}/setfavplant")
    public ResponseEntity<ResponseDTO<?>> setFavPlant(@PathVariable Long plantId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        plantService.setFavPlant(currentMemberId, plantId);
        String msg = "대표식물 설정 완료";
        return build(msg, HttpStatus.OK);
    }

    @DeleteMapping("/{plantId}")
    @Operation(summary = "식물 삭제",
            description = "해당 식물을 삭제함")
    public ResponseEntity<ResponseDTO<?>> deletePlant(@PathVariable Long plantId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        plantService.deletePlant(currentMemberId, plantId);
        String msg = "식물 삭제 완료";
        return build(msg, HttpStatus.OK);
    }

}
