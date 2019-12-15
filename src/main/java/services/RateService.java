package services;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import services.dao.entity.Money;
import services.dao.repository.MoneyCrudRepository;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RateService {
    @Autowired
    private RBCService rbcService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private MoneyCrudRepository moneyCrudRepository;

    // predict = k * value + b
    private Double k = null;
    private Double b = null;

    private void fit() throws IOException, ParseException {
        List<Pair<Long, Double>> ratesWithDate = rbcService.GetRatesWithDates("30");

        List<Double> rates = new ArrayList<>();
        List<Long> dates = new ArrayList<>();

        for (Pair<Long, Double> date2rate : ratesWithDate) {
            dates.add(date2rate.getKey());
            rates.add(date2rate.getValue());
        }
        List<Pair<Long, Double>> weatherWithDate = weatherService.GetWeatherByListDates(dates);

        List<Double> weather = new ArrayList<>();
        for (Pair<Long, Double> date2weather : weatherWithDate) {
            weather.add(date2weather.getValue());
        }

        k = CalculateScaling(rates, weather);
        b = CalculateShift(rates, weather);
    }

    @Transactional
    public Double predict(double temperature) throws IOException, ParseException {
        if (!(k != null && b != null)) {
            fit();
        }
        Optional<Money> money = moneyCrudRepository.findByTemperature(temperature);
        if (money.isPresent()) {
            return money.get().getMoney();
        }
        Double predict = k * temperature + b;
        moneyCrudRepository.save(new Money(temperature, predict));
        return k * temperature + b;
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
