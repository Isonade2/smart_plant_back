package wku.smartplant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.service.PlantHistoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arduino")
public class ArduinoController {

    private final PlantHistoryService plantHistoryService;

    @GetMapping("/test")
    public String test() {
        return "ok";
    }

    @GetMapping("/{uuid}")
    public String getStatus(@PathVariable("uuid") String uuid,
                            @RequestParam PlantHistoryDTO plantHistoryDTO) {
        plantHistoryService.writeHistory(uuid, plantHistoryDTO);
        return "ok";
    }
//    @GetMapping("/{uuid}")
//    public String getStatus(@PathVariable("uuid") String uuid,
//                            @RequestParam("temp") Double temp,
//                            @RequestParam("humidity") Double humidity,
//                            @RequestParam("water") Double water,
//                            @RequestParam("light") Double light) {
//        //checkStatus(temp, humidity, water, light);
//        return "ok";
//    }



}
