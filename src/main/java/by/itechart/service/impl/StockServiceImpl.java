package by.itechart.service.impl;

import by.itechart.model.domain.Stock;
import lombok.RequiredArgsConstructor;
import by.itechart.service.StockService;
import org.springframework.stereotype.Service;
import by.itechart.repository.StockRepository;
import by.itechart.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final CompanyRepository companyRepository;

    @Override
    public Optional<List<Stock>> findAllStock() {
        List<Stock> allStocks = (List<Stock>) stockRepository.findAll();
        return Optional.of(allStocks);
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public Optional<Stock> findByDateAndTicker(LocalDate date, String ticker) {
        return stockRepository.findByDateAndTicker(date, ticker);
    }

    @Override
    public Stock createStock(Stock stock) {
        stock.setCompany(companyRepository.findCompaniesByTicker(stock.getTicker()));
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(Stock stock) {
        stock.setCompany(companyRepository.findCompaniesByTicker(stock.getTicker()));
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStockById(Long id) {
        stockRepository.deleteById(id);
    }
}