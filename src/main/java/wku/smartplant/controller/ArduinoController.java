package wku.smartplant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.service.PlantHistoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arduino")
public class ArduinoController {

    private final PlantHistoryService plantHistoryService;

    @GetMapping("/{uuid}")
    public String getStatus(@PathVariable("uuid") String uuid,
                            @ModelAttribute PlantHistoryDTO plantHistoryDTO) {
        return plantHistoryService.saveHistoryByArduino(uuid, plantHistoryDTO);
    }


}
