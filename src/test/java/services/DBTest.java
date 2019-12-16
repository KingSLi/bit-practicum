package services;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import services.dao.entity.Weather;
import services.dao.repository.WeatherCrudRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBTest {

    @Autowired
    private WeatherCrudRepository weatherCrudRepository;

    @Test
    public void DBTest() throws JsonProcessingException {
        Weather weather = new Weather(12L,  34.0);
        weatherCrudRepository.save(weather);
        Optional<Weather> optional = weatherCrudRepository.findByTimestamp(12L);
        assert(optional.isPresent());
        assertEquals(34.0, optional.get().getTemperature(), 1e-4);
    }
}