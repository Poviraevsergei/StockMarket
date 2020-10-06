package by.itechart.service.impl;

import by.itechart.model.domain.Stock;
import by.itechart.repository.CompanyRepository;
import by.itechart.repository.StockRepository;
import by.itechart.service.StockService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    StockRepository stockRepository;
    CompanyRepository companyRepository;

    public StockServiceImpl(CompanyRepository companyRepository, StockRepository stockRepository) {
        this.stockRepository = stockRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Stock createStock(Stock stock) {
        stock.setCompany(companyRepository.findCompaniesByTicker(stock.getTicker()));
        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> findAllStock() {
        return (List<Stock>) stockRepository.findAll();
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return stockRepository.findById(id);
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