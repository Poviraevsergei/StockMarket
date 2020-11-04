package by.itechart.controller;

import by.itechart.model.domain.Stock;
import lombok.RequiredArgsConstructor;
import by.itechart.service.StockService;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import by.itechart.exception.CustomException;
import org.springframework.http.ResponseEntity;
import by.itechart.exception.ResourceNotFoundException;
import by.itechart.model.response.StockCandlesResponse;
import by.itechart.service.serviceFeign.StockServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import by.itechart.model.response.CompanyStockForTheDayResponse;
import by.itechart.model.response.CompanyStockForTheYearResponse;

import java.util.List;

import static by.itechart.utils.ProjectProperties.STOCK_NOT_FOUND;
import static by.itechart.utils.ProjectProperties.STOCKS_NOT_FOUND;
import static by.itechart.utils.ProjectProperties.STOCK_WAS_NOT_CREATED;
import static by.itechart.utils.ProjectProperties.STOCK_WAS_NOT_UPDATED;
import static by.itechart.utils.ProjectProperties.STOCK_CANDLES_NOT_UPDATED;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockController {

    private final StockServiceFeign stockServiceFeign;
    private final StockService stockService;

    @GetMapping("/getAllStocks")
    public ResponseEntity<List<Stock>> findAllStock() {
        List<Stock> allStocks = stockService.findAllStock().orElseThrow(() -> new ResourceNotFoundException(STOCKS_NOT_FOUND));
        return new ResponseEntity<>(allStocks, HttpStatus.OK);
    }

    @GetMapping("/getStock/{id}")
    public ResponseEntity<Stock> findById(@PathVariable Long id) {
        Stock stock = stockService.findById(id).orElseThrow(() -> new ResourceNotFoundException(STOCK_NOT_FOUND));
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping("/getCompanyStockForTheDay")
    public ResponseEntity<CompanyStockForTheDayResponse> getCompanyStockForTheDay(@RequestParam String ticker) {
        CompanyStockForTheDayResponse companyStockForTheDay = stockServiceFeign.getCompanyStockForTheDay(ticker)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_NOT_FOUND));
        return new ResponseEntity<>(companyStockForTheDay, HttpStatus.OK);
    }

    @GetMapping("/getCompanyStockForTheYear")
    public ResponseEntity<CompanyStockForTheYearResponse> getCompanyStockForTheYear(@RequestParam String ticker) {
        CompanyStockForTheYearResponse companyStockForTheYear = stockServiceFeign.getCompanyStockForTheYear(ticker)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_NOT_FOUND));
        return new ResponseEntity<>(companyStockForTheYear, HttpStatus.OK);
    }

    @GetMapping("/getStockCandles")
    public ResponseEntity<StockCandlesResponse> getStockCandles(String ticker, String from, String to, String resolution) {
        StockCandlesResponse stockCandles = stockServiceFeign.getStockCandles(ticker, from, to, resolution)
                .orElseThrow(() -> new ResourceNotFoundException(STOCK_CANDLES_NOT_UPDATED));
        return new ResponseEntity<>(stockCandles, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock resultStock = stockService.createStock(stock);
        if (resultStock == null) {
            throw new CustomException(STOCK_WAS_NOT_CREATED);
        }
        return new ResponseEntity<>(resultStock, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Stock> updateStock(@RequestBody Stock stock) {
        Stock resultStock = stockService.updateStock(stock);
        if (resultStock == null) {
            throw new CustomException(STOCK_WAS_NOT_UPDATED);
        }
        return new ResponseEntity<>(resultStock, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStock(@PathVariable Long id) {
        stockService.deleteStockById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
