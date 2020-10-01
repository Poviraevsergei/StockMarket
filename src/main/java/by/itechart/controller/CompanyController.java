package by.itechart.controller;

import by.itechart.response.AllCompaniesResponse;
import by.itechart.response.CompanyFinancialReportResponse;
import by.itechart.response.CompanyNewsResponse;
import by.itechart.response.CompanyResponse;
import by.itechart.serviceFeign.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/getAllCompanies")
    public List<AllCompaniesResponse> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/getCompany")
    public CompanyResponse getCompany(String ticker) {
        return companyService.getCompany(ticker);
    }

    @GetMapping("/getCompanyNews")
    public List<CompanyNewsResponse> getCompanyNews(String ticker, String from, String to) {
        return companyService.getCompanyNews(ticker, from, to);
    }

    @GetMapping("/getFinancialReport")
    public CompanyFinancialReportResponse getFinancialReport(String ticker) {
        return companyService.getFinancialReport(ticker);
    }
}