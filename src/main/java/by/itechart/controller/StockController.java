package by.itechart.controller;

import by.itechart.response.CompanyStockForTheDayResponse;
import by.itechart.response.CompanyStockForTheYearResponse;
import by.itechart.response.StockCandlesResponse;
import by.itechart.serviceFeign.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/stock")
public class StockController {
    StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/getCompanyStockForTheDay")
    public CompanyStockForTheDayResponse getCompanyStockForTheDay(String ticker) {
        return stockService.getCompanyStockForTheDay(ticker);
    }

    @GetMapping("/getCompanyStockForTheYear")
    public CompanyStockForTheYearResponse getCompanyStockForTheYear(String ticker) {
        return stockService.getCompanyStockForTheYear(ticker);
    }

    @GetMapping("/getStockCandles")
    public StockCandlesResponse getStockCandles(String ticker, String from, String to, String resolution) {
        return stockService.getStockCandles(ticker, from, to, resolution);
    }
}