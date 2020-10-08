package by.itechart.service.impl;

import by.itechart.model.domain.Stock;
import lombok.RequiredArgsConstructor;
import by.itechart.service.StockService;
import org.springframework.stereotype.Service;
import by.itechart.repository.StockRepository;
import by.itechart.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final CompanyRepository companyRepository;

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