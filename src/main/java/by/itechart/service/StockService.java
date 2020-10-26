package by.itechart.service;

import by.itechart.model.domain.Stock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StockService {

    Optional<List<Stock>> findAllStock();

    Optional<Stock> findById(Long id);

    Optional<Stock> findByDateAndTicker(LocalDate date, String ticker);

    Stock createStock(Stock stock);

    Stock updateStock(Stock stock);

    void deleteStockById(Long id);
}