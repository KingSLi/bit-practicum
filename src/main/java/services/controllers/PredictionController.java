package services.controllers;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import services.RateService;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class PredictionController {
    @Autowired
    private RateService rateService;

    @PostMapping(path="/predict")
    public String GetPredicrt(int temperature) throws IOException, ParseException, JSONException {
        return "with temperature " + temperature + ", Dollar will be - " + rateService.predict(temperature);
    }
}
