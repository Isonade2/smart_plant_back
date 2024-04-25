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

    @GetMapping("/{uuid}")
    public String savePlantHistoryAndReturnWaterState(@PathVariable("uuid") String uuid,
                            @ModelAttribute PlantHistoryDTO plantHistoryDTO) {
        return arduinoService.saveHistoryByArduino(uuid, plantHistoryDTO);
    }

    @GetMapping("/{uuid}/water")
    public String getWaterState(@PathVariable("uuid") String uuid) {
        return arduinoService.checkWaterState(uuid);
    }

}
