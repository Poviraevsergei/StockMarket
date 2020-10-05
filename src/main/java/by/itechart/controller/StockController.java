package by.itechart.controller;

import by.itechart.model.domain.Stock;
import by.itechart.model.response.CompanyStockForTheDayResponse;
import by.itechart.model.response.CompanyStockForTheYearResponse;
import by.itechart.model.response.StockCandlesResponse;
import by.itechart.service.StockService;
import by.itechart.serviceFeign.StockServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/stock")
public class StockController {
    StockServiceFeign stockServiceFeign;
    StockService stockService;

    public StockController(StockServiceFeign stockServiceFeign, StockService stockService) {
        this.stockServiceFeign = stockServiceFeign;
        this.stockService = stockService;
    }

    @GetMapping("/getAllStocks")
    public List<Stock> findAllStock() {
        return stockService.findAllStock();
    }

    @GetMapping("/{id}")
    public Optional<Stock> findById(@PathVariable Long id) {
        return stockService.findById(id);
    }

    @GetMapping("/getCompanyStockForTheDay")
    public CompanyStockForTheDayResponse getCompanyStockForTheDay(String ticker) {
        return stockServiceFeign.getCompanyStockForTheDay(ticker);
    }

    @GetMapping("/getCompanyStockForTheYear")
    public CompanyStockForTheYearResponse getCompanyStockForTheYear(String ticker) {
        return stockServiceFeign.getCompanyStockForTheYear(ticker);
    }

    @GetMapping("/getStockCandles")
    public StockCandlesResponse getStockCandles(String ticker, String from, String to, String resolution) {
        return stockServiceFeign.getStockCandles(ticker, from, to, resolution);
    }

    @PostMapping("/create")
    public Stock createStock(Stock stock) {
        return stockService.createStock(stock);
    }

    @PutMapping("/update")
    public Stock updateStock(Stock stock) {
        return stockService.updateStock(stock);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStockById(id);
    }
}