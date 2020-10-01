package by.itechart.serviceFeign;

import by.itechart.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "https://finnhub.io/api/v1", name = "Company")
public interface CompanyService {

    @GetMapping("/stock/symbol?exchange=US&token=btqbebn48v6t9hdd6cog")
    List<AllCompaniesResponse> getAllCompanies();

    @GetMapping("/stock/profile2?token=btqbebn48v6t9hdd6cog&symbol={ticker}")
    CompanyResponse getCompany(@PathVariable String ticker);

    @GetMapping("/company-news?symbol={ticker}&from={from}&to={to}&token=btqbebn48v6t9hdd6cog")
    List<CompanyNewsResponse> getCompanyNews(@PathVariable String ticker, @PathVariable String from, @PathVariable String to);

    @GetMapping("/stock/financials-reported?symbol={ticker}&token=btqbebn48v6t9hdd6cog")
    CompanyFinancialReportResponse getFinancialReport(@PathVariable String ticker);
}