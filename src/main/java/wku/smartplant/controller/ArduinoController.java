package wku.smartplant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.service.ArduinoService;
import wku.smartplant.service.DiscordService;
import wku.smartplant.service.PlantHistoryService;
import wku.smartplant.service.PlantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arduino")
public class ArduinoController {

    private final ArduinoService arduinoService;
    private final DiscordService discordService;

    @GetMapping("/{uuid}")
    public String savePlantHistoryAndReturnWaterState(@PathVariable("uuid") String uuid,
                            @ModelAttribute PlantHistoryDTO plantHistoryDTO) {

        discordService.sendDiscordMessage(plantHistoryDTO.toString());
        return arduinoService.saveHistoryByArduino(uuid, plantHistoryDTO);
    }

    @GetMapping("/{uuid}/water")
    public String getWaterState(@PathVariable("uuid") String uuid) {
        return arduinoService.checkWaterState(uuid);
    }

}
