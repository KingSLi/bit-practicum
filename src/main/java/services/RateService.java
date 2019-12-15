package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RateService {
    @Autowired
    private RBCService rbcService;
    @Autowired
    private WeatherService weatherService;

    // predict = k * value + b
    private Double k = null;
    private Double b = null;

    private void fit() throws IOException {
        String useLastDays = "30";
        List<Double> rates = rbcService.GetRatesForLastDays(useLastDays);
        List<Double> weather = weatherService.GetWeatherForLastDays(30);

        k = CalculateScaling(rates, weather);
        b = CalculateShift(rates, weather);


    }

    public Double predict(double value) throws IOException {
        if (!(k != null && b != null)) {
            fit();
        }
        return k * value + b;
    }

    private Double CalculateShift(List<Double> rates, List<Double> weather) {
        return CalculateMean(rates) - CalculateScaling(rates, weather) * CalculateMean(weather);
    }

    private Double CalculateScaling(List<Double> rates, List<Double> weather) {
        Double numerator = 0.;
        Double denominator = 0.;
        Double meanRate = CalculateMean(rates);
        Double meanTemperature = CalculateMean(weather);
        for (int i = 0; i < rates.size(); i++) {
            numerator += (rates.get(i) - meanRate) * (weather.get(i) - meanTemperature);
            denominator += Math.pow(rates.get(i) - meanRate, 2);
        }
        return numerator / denominator;
    }

    private double CalculateMean(List<Double> arr) {
        int count = 0;
        double sum = 0;
        for (Double value : arr) {
            sum += value;
            ++count;
        }
        return sum / count;
    }
}
