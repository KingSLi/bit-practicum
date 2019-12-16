package services;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import services.dao.entity.Weather;
import services.dao.repository.WeatherCrudRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DBTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private WeatherCrudRepository weatherCrudRepository;

    @Test
    public void DBTest() throws JsonProcessingException {
        Weather weather = new Weather(12L,  34.0);
        Optional<Weather> optionalWeather = Optional.of(weather);
        Mockito.when(weatherCrudRepository.findByTimestamp(any())).thenReturn(optionalWeather);
        assertEquals(34.0, (weatherService.GetWeatherByListDates(Arrays.asList(12L))).get(0).getValue(), 1e-4);
    }
}