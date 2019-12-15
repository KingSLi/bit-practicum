package services.dao.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import services.dao.entity.Money;

import java.util.Optional;

@Repository
public interface MoneyCrudRepository extends CrudRepository<Money, Double> {
    Optional<Money> findByTemperature(Double temp);
}