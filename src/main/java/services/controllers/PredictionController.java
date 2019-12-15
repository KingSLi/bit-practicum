package services.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.RateService;

import java.io.IOException;
import java.text.ParseException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PredictionController {
    @Autowired
    private RateService rateService;

    @RequestMapping(path="/predict", method = POST)
    public String GetPredicrt(int temperature) throws IOException, ParseException {
        return "with temperature " + temperature + ", Dollar will be - " + rateService.predict(temperature);
    }
}
