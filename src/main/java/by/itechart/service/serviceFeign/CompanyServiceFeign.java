package by.itechart.service.serviceFeign;

import java.util.List;
import java.util.Optional;

import by.itechart.model.response.CompanyResponse;
import by.itechart.model.response.CompanyNewsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import by.itechart.model.response.AllCompaniesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import by.itechart.model.response.CompanyFinancialReportResponse;

@FeignClient(url = "https://finnhub.io/api/v1", name = "company")
public interface CompanyServiceFeign {

    @GetMapping("/stock/symbol?exchange=US&token=btqbebn48v6t9hdd6cog")
    Optional<List<AllCompaniesResponse>> getAllCompanies();

    @GetMapping("/stock/profile2?token=btqbebn48v6t9hdd6cog&symbol={ticker}")
    Optional<CompanyResponse> getCompany(@PathVariable String ticker);

    @GetMapping("/company-news?symbol={ticker}&from={from}&to={to}&token=btqbebn48v6t9hdd6cog")
    Optional<List<CompanyNewsResponse>> getCompanyNews(@PathVariable String ticker, @PathVariable String from, @PathVariable String to);

    @GetMapping("/stock/financials-reported?symbol={ticker}&token=btqbebn48v6t9hdd6cog")
    Optional<CompanyFinancialReportResponse> getFinancialReport(@PathVariable String ticker);
}