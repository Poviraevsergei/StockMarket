package by.itechart.service;

import by.itechart.model.domain.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {
    Stock createStock(Stock stock);

    List<Stock> findAllStock();

    Optional<Stock> findById(Long id);

    Stock updateStock(Stock stock);

    void deleteStockById(Long id);
}