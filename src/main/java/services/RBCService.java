package services;


import javafx.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class RBCService {

    private List<Double> GetRatesForLastDays(String date) {
        return ParseResponse(CollectData(date));
    }

    public List<Pair<Long, Double>> GetRatesWithDates(String date) throws ParseException {
        return ParseResponseWithDates(CollectData(date));
    }

    private String CollectData(String period) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&separator=,&data_format=BROWSER&lastdays=";
        ResponseEntity<String> response =
                restTemplate.getForEntity(url + period, String.class);

        assert (response.getStatusCode().equals(HttpStatus.OK));
        return response.getBody();
    }

    private List<Double> ParseResponse(String data) {
        List<Double> response = new ArrayList<>();
        String[] rows = data.split("\n");
        for (String row : rows) {
            String[] line = row.split(",");
            response.add(Double.parseDouble(line[line.length - 1]));
        }
        return response;
    }

    private List<Pair<Long, Double>> ParseResponseWithDates(String data) throws ParseException {
        List<Pair<Long, Double>> response = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        String[] rows = data.split("\n");
        for (String row : rows) {
            String[] line = row.split(",");
            Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(line[1]);
            calendar.setTime(tempDate);
            response.add(new Pair<>(calendar.getTimeInMillis() / 1000, Double.parseDouble(line[line.length - 1])));
        }
        return response;
    }

    public Double GetMaxRate() {
        List<Double> rates = GetRatesForLastDays("30");
        double max = Double.MIN_VALUE;
        for (Double rate : rates) {
            max = Math.max(rate, max);
        }
        return max;
    }
}
