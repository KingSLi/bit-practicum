package service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication
public class RateService {

    public String predict() {
        RestTemplate restTemplate = new RestTemplate();

        String rbkUrl = "http://rbc:8091/rbc";
        ResponseEntity<String> rbkResponse = restTemplate.getForEntity(rbkUrl, String.class);

        String weatherUrl = "http://weather:8090/weather";
        ResponseEntity<String> weatherResponse = restTemplate.getForEntity(weatherUrl, String.class);
        return "RBK : " + rbkResponse.getBody() + "\n\nWeather : " + weatherResponse.getBody() ;
    }


    public static void main(String[] args) {
        SpringApplication.run(RateService.class, args);
    }

}
