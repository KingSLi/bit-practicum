package services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.WeatherService;

import java.io.IOException;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/weather")
    public String weather() throws IOException {
        return "Now temperature is about " + weatherService.GetCurrentTemperature();
    }
}
