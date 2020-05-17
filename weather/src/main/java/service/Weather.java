package service;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Weather")
public class Weather {

    @Id
    private Long timestamp;
    private Double temperature;

    public Weather(Long timestamp, double temp) {
        this.timestamp = timestamp;
        this.temperature = temp;
    }

    public Weather() {}

    public Double getTemperature() {
        return temperature;
    }
}