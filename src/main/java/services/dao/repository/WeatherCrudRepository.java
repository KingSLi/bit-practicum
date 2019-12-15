package services.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import services.dao.entity.Weather;

import java.util.Optional;

@Repository
public interface WeatherCrudRepository extends CrudRepository<Weather, Long> {
    Optional<Weather> findByTimestamp(Long timestamp);
}
