package by.itechart.service.impl;

import by.itechart.model.domain.Stock;
import lombok.RequiredArgsConstructor;
import by.itechart.service.StockService;
import by.itechart.exception.CustomException;
import org.springframework.stereotype.Service;
import by.itechart.repository.StockRepository;
import by.itechart.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static by.itechart.utils.ProjectProperties.COMPANY_NOT_FOUND;

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
    public Optional<Stock> findByDateAndTicker(String date, String ticker) {
        LocalDate localDate = LocalDate.parse(date);
        return stockRepository.findByDateAndTicker(localDate, ticker);
    }

    @Override
    public Stock createStock(Stock stock) {
        stock.setCompany(companyRepository.findCompaniesByTicker(stock.getTicker()).orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND)));
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(Stock stock) {
        stock.setCompany(companyRepository.findCompaniesByTicker(stock.getTicker()).orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND)));
        return stockRepository.save(stock);
    }

    @Override
    public void deleteStockById(Long id) {
        stockRepository.deleteById(id);
    }
}
