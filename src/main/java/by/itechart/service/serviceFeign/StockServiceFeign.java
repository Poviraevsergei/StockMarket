package by.itechart.service.serviceFeign;

import org.springframework.cloud.openfeign.FeignClient;
import by.itechart.model.response.StockCandlesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import by.itechart.model.response.CompanyStockForTheDayResponse;
import by.itechart.model.response.CompanyStockForTheYearResponse;

import java.util.Optional;

@FeignClient(url = "https://finnhub.io/api/v1", name = "Stock")
public interface StockServiceFeign {

    @GetMapping("/quote?symbol={ticker}&token=btqbebn48v6t9hdd6cog")
    Optional<CompanyStockForTheDayResponse> getCompanyStockForTheDay(@PathVariable String ticker);

    @GetMapping("/stock/metric?symbol={ticker}&metric=all&token=btqbebn48v6t9hdd6cog")
    Optional<CompanyStockForTheYearResponse> getCompanyStockForTheYear(@PathVariable String ticker);

    @GetMapping("/stock/candle?symbol={ticker}&resolution={resolution}&from={from}&to={to}&token=btqbebn48v6t9hdd6cog")
    StockCandlesResponse getStockCandles(@PathVariable String ticker, @PathVariable String from, @PathVariable String to, @PathVariable String resolution);
}
