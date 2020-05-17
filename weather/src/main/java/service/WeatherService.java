package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import service.structure.WeatherResponse;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class WeatherService {

    @Autowired
    private WeatherCrudRepository weatherCrudRepository;

    @Transactional
    public List<AbstractMap.SimpleEntry <Long, Double>> GetWeatherByListDates(List<Long> dates) throws IOException, JSONException {
        List<AbstractMap.SimpleEntry <Long, Double>> weathersList = new ArrayList<>();
        for (Long date : dates) {
            Optional<Weather> data = weatherCrudRepository.findByTimestamp(date);
            if (data.isPresent()) {
                weathersList.add(new AbstractMap.SimpleEntry <>(date, data.get().getTemperature()));
                continue;
            }
            WeatherResponse gettedWeather = parseResponse(GetResponse("" + date));
            weathersList.add(new AbstractMap.SimpleEntry<>(date, GetDailyTemp(gettedWeather)));
            weatherCrudRepository.save(new Weather(date, GetDailyTemp(gettedWeather)));
        }
        System.out.println(weathersList.toString());
        return weathersList;
    }

    @Transactional
    public double GetCurrentTemperature() throws IOException, JSONException {
        long curTime = System.currentTimeMillis() / 1000L;
        Optional<Weather> data = weatherCrudRepository.findByTimestamp(curTime);
        if (data.isPresent()) {
            return data.get().getTemperature();
        }
        WeatherResponse weather = parseResponse(GetResponse("" + curTime));
        weatherCrudRepository.save(new Weather(curTime, weather.currently.temperature));
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

    private WeatherResponse parseResponse(String response) throws JSONException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, WeatherResponse.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(WeatherService.class, args);
    }

}
