package service;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoneyCrudRepository extends CrudRepository<Money, Double> {
    Optional<Money> findByTemperature(Double temp);
}