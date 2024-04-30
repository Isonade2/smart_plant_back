package wku.smartplant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.service.ArduinoService;
import wku.smartplant.service.PlantHistoryService;
import wku.smartplant.service.PlantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arduino")
public class ArduinoController {

    private final ArduinoService arduinoService;
    private int getCount = 0; //저장 횟수를 줄이기 위해. 배포 시 삭제

    @GetMapping("/{uuid}")
    public String savePlantHistoryAndReturnWaterState(@PathVariable("uuid") String uuid,
                            @ModelAttribute PlantHistoryDTO plantHistoryDTO) {
        if (getCount % 15 == 0) {
            return arduinoService.saveHistoryByArduino(uuid, plantHistoryDTO);
        }
        return "not saved";
    }

    @GetMapping("/{uuid}/water")
    public String getWaterState(@PathVariable("uuid") String uuid) {
        return arduinoService.checkWaterState(uuid);
    }

}
