package wku.smartplant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseDTO;

@RestController
@RequestMapping("/arduino")
public class ArduinoController {

    @GetMapping("/{uuid}/")
    public String getSensor(@PathVariable("uuid") String uuid,
                            @RequestParam("temp") Double temp,
                            @RequestParam("humidity") Double humidity) {

        System.out.println("uuid = " + uuid);

        return "ok";
    }


}
