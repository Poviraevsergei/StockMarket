package by.itechart.repository;

import by.itechart.model.domain.Stock;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
}