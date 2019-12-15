package services.weather.response.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeather {
    public ArrayList<InnerDailyWeather> data;
}
