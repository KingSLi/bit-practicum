package service.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InnerDailyWeather {
    public double temperatureHigh;
    public long time;
}
