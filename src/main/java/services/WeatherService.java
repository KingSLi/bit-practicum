package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import services.weather.response.structure.WeatherResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherService {

    List<Double> GetWeatherForLastDays(Integer days) throws IOException {
        List<Double> weathersList = new ArrayList<>();
        long curTime = System.currentTimeMillis() / 1000L;
        long oneDay = 86400L;
        for (long i = 0; i < 20; ++i) {
            WeatherResponse weather = parseResponse(GetResponse("" + (curTime - i * oneDay)));
            weathersList.add(GetDailyTemp(weather));
        }
        return weathersList;
    }

    public double GetCurrentTemperature() throws JsonProcessingException {
        long curTime = System.currentTimeMillis() / 1000L;
        WeatherResponse weather = parseResponse(GetResponse("" + curTime));
        return weather.currently.temperature;
    }

    private double GetDailyTemp(WeatherResponse weatherResponse) {
        return weatherResponse.daily.data.get(0).temperatureHigh;
    }

    private String GetResponse(String date) {
        String secret = "0feca2acae8964e6f2f54ba5260e3952";
        String api = "https://api.darksky.net/forecast/";
        String coordinates = "55.751244,37.618423";

        RestTemplate restTemplate = new RestTemplate();
        String url = api + secret + "/" + coordinates + "," + date;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assert (response.getStatusCode().equals(HttpStatus.OK));
        return response.getBody();
    }

    private WeatherResponse parseResponse(String response) throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, WeatherResponse.class);
    }

}
