package service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class RBCService {

    private static String fixData = "USD000000TOD,2019-09-24,63.735,63.7875,63.6,63.7825,492878000,63.6845\n" +
            "USD000000TOD,2019-09-25,64.0375,64.4775,63.9925,64.4,944253000,64.2578\n" +
            "USD000000TOD,2019-09-26,64.32,64.37,64.095,64.3325,603439000,64.2645\n" +
            "USD000000TOD,2019-09-27,64.34,64.4375,64.26,64.305,727986000,64.3603\n" +
            "USD000000TOD,2019-09-30,64.65,65,64.57,64.96,826782000,64.7697\n" +
            "USD000000TOD,2019-10-01,64.955,65.37,64.95,65.3475,699118000,65.1026\n" +
            "USD000000TOD,2019-10-02,65.3475,65.5575,65.0225,65.2375,647424000,65.3354\n" +
            "USD000000TOD,2019-10-03,65.085,65.3675,65.0225,65.2175,652828000,65.1126\n" +
            "USD000000TOD,2019-10-04,65.01,65.0925,64.51,64.5375,814237000,64.8713\n" +
            "USD000000TOD,2019-10-07,64.675,64.97,64.675,64.8575,515837000,64.8634\n" +
            "USD000000TOD,2019-10-08,64.8375,65.3625,64.7525,65.2875,652387000,65.058\n" +
            "USD000000TOD,2019-10-09,65.19,65.195,64.8075,64.9,622432000,64.981\n" +
            "USD000000TOD,2019-10-10,64.7,64.87,64.545,64.585,553196000,64.7404\n" +
            "USD000000TOD,2019-10-11,64.265,64.2775,63.9725,64.11,851068000,64.1311\n" +
            "USD000000TOD,2019-10-15,64.2875,64.5,64.1825,64.25,638537000,64.3507\n" +
            "USD000000TOD,2019-10-16,64.3,64.4425,64.14,64.205,595571000,64.2976\n" +
            "USD000000TOD,2019-10-17,64.1125,64.13,63.8375,63.915,828493000,63.9875\n" +
            "USD000000TOD,2019-10-18,64.005,64.0675,63.8825,64.0625,554766000,63.9417\n" +
            "USD000000TOD,2019-10-21,63.81,63.8475,63.62,63.7275,670843000,63.7217\n" +
            "USD000000TOD,2019-10-22,63.695,63.8025,63.56,63.68,606826000,63.6713\n" +
            "USD000000TOD,2019-10-23,63.74,63.9825,63.69,63.9425,770583000,63.8508";

    public List<Double> GetRatesForLastDays(String date) {
        return ParseResponse(CollectData(date));
    }

    public List<AbstractMap.SimpleEntry <Long, Double>> GetRatesWithDates(String date) throws ParseException {
        return ParseResponseWithDates(CollectData(date));
    }

    public String CollectData(String period) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&separator=,&data_format=BROWSER&lastdays=";
        try {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(url + period, String.class);
            return response.getBody();
        } catch (Exception e) {
            return fixData;
        }
    }

    public List<Double> ParseResponse(String data) {
        List<Double> response = new ArrayList<>();
        String[] rows = data.split("\n");
        for (String row : rows) {
            String[] line = row.split(",");
            response.add(Double.parseDouble(line[line.length - 1]));
        }
        return response;
    }

    private List<AbstractMap.SimpleEntry <Long, Double>> ParseResponseWithDates(String data) throws ParseException {
        List<AbstractMap.SimpleEntry <Long, Double>> response = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        String[] rows = data.split("\n");
        for (String row : rows) {
            String[] line = row.split(",");
            Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(line[1]);
            calendar.setTime(tempDate);
            response.add(new AbstractMap.SimpleEntry <> (calendar.getTimeInMillis() / 1000, Double.parseDouble(line[line.length - 1])));
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


    public static void main(String[] args) {
        SpringApplication.run(RBCService.class, args);
    }
}
