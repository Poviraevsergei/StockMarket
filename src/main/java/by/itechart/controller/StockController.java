package by.itechart.controller;

import by.itechart.model.domain.Stock;
import lombok.RequiredArgsConstructor;
import by.itechart.service.StockService;
import by.itechart.model.response.StockCandlesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import by.itechart.service.serviceFeign.StockServiceFeign;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import by.itechart.model.response.CompanyStockForTheDayResponse;
import by.itechart.model.response.CompanyStockForTheYearResponse;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping(value = "/stock")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StockController {

    private final StockServiceFeign stockServiceFeign;
    private final StockService stockService;

    @GetMapping("/getAllStocks")
    public List<Stock> findAllStock() {
        return stockService.findAllStock();
    }

    @GetMapping("/{id}")
    public Optional<Stock> findById(@PathVariable Long id) {
        return stockService.findById(id);
    }

    @GetMapping("/getCompanyStockForTheDay")
    public Optional<CompanyStockForTheDayResponse> getCompanyStockForTheDay(@RequestParam String ticker) {
        return stockServiceFeign.getCompanyStockForTheDay(ticker);
    }

    @GetMapping("/getCompanyStockForTheYear")
    public Optional<CompanyStockForTheYearResponse> getCompanyStockForTheYear(@RequestParam String ticker) {
        return stockServiceFeign.getCompanyStockForTheYear(ticker);
    }

    @GetMapping("/getStockCandles")
    public StockCandlesResponse getStockCandles(String ticker, String from, String to, String resolution) {
        return stockServiceFeign.getStockCandles(ticker, from, to, resolution);
    }

    @PostMapping(value = "/create")
    public Stock createStock(@RequestBody Stock stock) {
        return stockService.createStock(stock);
    }

    @PutMapping(value = "/update")
    public Stock updateStock(@RequestBody Stock stock) {
        return stockService.updateStock(stock);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStockById(id);
    }
}