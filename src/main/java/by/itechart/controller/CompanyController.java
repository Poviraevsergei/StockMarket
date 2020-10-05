package by.itechart.controller;

import by.itechart.model.domain.Company;
import by.itechart.model.response.AllCompaniesResponse;
import by.itechart.model.response.CompanyFinancialReportResponse;
import by.itechart.model.response.CompanyNewsResponse;
import by.itechart.model.response.CompanyResponse;
import by.itechart.service.CompanyService;
import by.itechart.serviceFeign.CompanyServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyController {

    CompanyServiceFeign companyServiceFeign;
    CompanyService companyService;

    public CompanyController(CompanyServiceFeign companyServiceFeign, CompanyService companyService) {
        this.companyServiceFeign = companyServiceFeign;
        this.companyService = companyService;
    }

    @GetMapping("/getCompanyByTicker/{ticker}")
    public Company getCompanyByTicker(@PathVariable String ticker) {
        return companyService.findCompanyFromDbByTicker(ticker);
    }

    @GetMapping("/getAllComaniesFromDb")
    public List<Company> getAllCompaniesFromDb() {
        return companyService.findAllCompanyFromDb();
    }

    @GetMapping("/getAllCompanies")
    public List<AllCompaniesResponse> getAllCompanies() {
        return companyServiceFeign.getAllCompanies();
    }

    @GetMapping("/getCompany")
    public CompanyResponse getCompany(String ticker) {
        return companyServiceFeign.getCompany(ticker);
    }

    @GetMapping("/getCompanyNews")
    public List<CompanyNewsResponse> getCompanyNews(String ticker, String from, String to) {
        return companyServiceFeign.getCompanyNews(ticker, from, to);
    }

    @GetMapping("/getFinancialReport")
    public CompanyFinancialReportResponse getFinancialReport(String ticker) {
        return companyServiceFeign.getFinancialReport(ticker);
    }

    @GetMapping("/{id}")
    public Optional<Company> findCompanyById(@PathVariable Long id) {
        return companyService.findCompanyById(id);
    }

    @PostMapping("/create")
    public Company createCompany(Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/update")
    public Company updateCompany(Company company) {
        return companyService.updateCompany(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
    }
}