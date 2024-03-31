package wku.smartplant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseDTO;

@RestController
@RequestMapping("/arduino")
public class ArduinoController {

    @GetMapping("/test")
    public String test() {
        return "ok";
    }

    @GetMapping("/{uuid}")
    public String getSensor(@PathVariable("uuid") String uuid,
                            @RequestParam("temp") Double temp,
                            @RequestParam("humidity") Double humidity) {

        System.out.println("uuid = " + uuid);

        return "ok";
    }

    @GetMapping("/status/{uuid}")
    public String getStatus(@PathVariable("uuid") String uuid,
                            @RequestParam("temp") Double temp,
                            @RequestParam("humidity") Double humidity,
                            @RequestParam("water") Double water,
                            @RequestParam("light") Double light) {
        //checkStatus(temp, humidity, water, light);
        return "ok";
    }



}
