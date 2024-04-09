package wku.smartplant.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<ResponseDTO<?>> join(PlantRequestDTO plantRequestDTO){
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

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getPlantList() {
        return build("식물 리스트", HttpStatus.OK, plantService.getAllPlants());
    }
}
