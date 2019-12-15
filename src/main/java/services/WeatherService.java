package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import services.dao.entity.Weather;
import services.dao.repository.WeatherCrudRepository;
import services.weather.response.structure.WeatherResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class WeatherService {

    @Autowired
    private WeatherCrudRepository weatherCrudRepository;

    @Transactional
    public List<Pair<Long, Double>> GetWeatherByListDates(List<Long> dates) throws JsonProcessingException {
        List<Pair<Long, Double>> weathersList = new ArrayList<>();
        for (Long date : dates) {
            Optional<Weather> data = weatherCrudRepository.findByTimestamp(date);
            if (data.isPresent()) {
                weathersList.add(new Pair<>(date, data.get().getTemperature()));
                continue;
            }
            WeatherResponse gettedWeather = parseResponse(GetResponse("" + date));
            weathersList.add(new Pair<>(date, GetDailyTemp(gettedWeather)));
            weatherCrudRepository.save(new Weather(date, GetDailyTemp(gettedWeather)));
        }
        System.out.println(weathersList.toString());
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

    private Long GetTime(WeatherResponse weatherResponse) {
        return weatherResponse.daily.data.get(0).time;
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
