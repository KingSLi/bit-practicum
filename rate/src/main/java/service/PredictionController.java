package service;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class PredictionController {
    @Autowired
    private RateService rateService;

    @GetMapping(path="/predict")
    public String GetPredicrt() throws IOException, ParseException, JSONException {
        return "Predict " + rateService.predict();
    }
}
