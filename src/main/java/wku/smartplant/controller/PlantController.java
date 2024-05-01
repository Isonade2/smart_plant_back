package wku.smartplant.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.plant.PlantRequestDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.AuthenticationSevice;
import wku.smartplant.service.PlantService;


import static wku.smartplant.dto.ResponseEntityBuilder.*;

@RestController
@RequestMapping("/plant")
@RequiredArgsConstructor
@Slf4j
public class PlantController {

    private final PlantService plantService;
    private final AuthenticationSevice authenticationSevice;

    @GetMapping("/test")
    public ResponseEntity<ResponseDTO<?>> test(){
        int num = 2;
        return build("test", HttpStatus.OK,num);
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO<?>> join(@Valid @RequestBody PlantRequestDTO plantRequestDTO){
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
    public ResponseEntity<ResponseDTO<?>> changeWaterState(@PathVariable Long plantId) {
        //Long currentMemberId = SecurityUtil.getCurrentMemberId(); 테스트 할떄만 주석
        Boolean changedState = plantService.changeGiveWater(1L, plantId); //바뀐 상태
        String msg = "바뀐 상태는 " + changedState + "입니다.";
        return build(msg, HttpStatus.OK, changedState);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getPlantList() {
        return build("식물 리스트", HttpStatus.OK, plantService.getAllPlants());
    }
}
