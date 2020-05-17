package service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherCrudRepository extends CrudRepository<Weather, Long> {
    Optional<Weather> findByTimestamp(Long timestamp);
}
