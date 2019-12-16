package services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import services.WeatherService;

import java.io.IOException;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public String weather() throws IOException {
        return "Now temperature is about " + weatherService.GetCurrentTemperature();
    }
}
