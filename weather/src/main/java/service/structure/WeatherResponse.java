package service.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    public CurrentlyWeather currently;
    public DailyWeather daily;
}
