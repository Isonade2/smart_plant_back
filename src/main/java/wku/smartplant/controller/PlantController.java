package wku.smartplant.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.plant.PlantDTO;
import wku.smartplant.dto.plant.PlantRequestDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.AuthenticationSevice;
import wku.smartplant.service.PlantService;


import java.util.List;

import static wku.smartplant.dto.ResponseEntityBuilder.*;

@RestController
@RequestMapping("/plant")
@RequiredArgsConstructor
@Slf4j
public class PlantController {

    private final PlantService plantService;
    private final AuthenticationSevice authenticationSevice;

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO<Long>> join(PlantRequestDTO plantRequestDTO) {
        log.info("join");
        log.info(plantRequestDTO.toString());
        Long currentMemberId = SecurityUtil.getCurrentMemberId();

        Long plantid = plantService.createPlant(plantRequestDTO, currentMemberId);


        return build("식물 등록 성공", HttpStatus.OK, plantid);
//        try{
//            Long id = authenticationSevice.isLoggedIn();
//        } catch (Exception e){
//            return build(e.getMessage(), HttpStatus.UNAUTHORIZED);
//        }
    }

    @GetMapping("/{plantId}/water")
    public ResponseEntity<ResponseDTO<Boolean>> changeWaterState(@PathVariable Long plantId) {
        //Long currentMemberId = SecurityUtil.getCurrentMemberId(); 테스트 할떄만 주석
        Boolean changedState = plantService.changeGiveWater(1L, plantId); //바뀐 상태
        String msg = "바뀐 상태는 " + changedState + "입니다.";
        return build(msg, HttpStatus.OK, changedState);
    }

    @GetMapping
    @Operation(summary = "특정 멤버의 식물 정보 얻기",
            description = "요청 시 멤버 토큰 필요. 해당 멤버의 식물들을 리턴해줌",
            responses = {
                    @ApiResponse(responseCode = "201", description = ""),
                    @ApiResponse(responseCode = "400", description = "가입 이력이 없을 경우, 가입은 했는데 활성화를 안했을 경우" +
                            "<br> 정규식 에러 시에는 다음 내용을 content에 포함 content : {email : '메세지'} ")
            })
    public ResponseEntity<ResponseDTO<List<PlantDTO>>> getPlantList() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.<List<PlantDTO>>builder().message("식물 리스트").content(plantService.getAllPlants()).build());
        //return build("식물 리스트", HttpStatus.OK, plantService.getAllPlants());
    }
}
