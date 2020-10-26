package by.itechart.repository;

import by.itechart.model.domain.Stock;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
    Optional<Stock> findByDateAndTicker(LocalDate date, String ticker);
}