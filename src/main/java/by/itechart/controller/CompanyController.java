package by.itechart.controller;

import lombok.RequiredArgsConstructor;
import by.itechart.model.domain.Company;
import by.itechart.service.CompanyService;
import by.itechart.model.response.CompanyResponse;
import by.itechart.serviceFeign.CompanyServiceFeign;
import by.itechart.model.response.CompanyNewsResponse;
import by.itechart.model.response.AllCompaniesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import by.itechart.model.response.CompanyFinancialReportResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyController {

    private final CompanyServiceFeign companyServiceFeign;
    private final CompanyService companyService;

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
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/update")
    public Company updateCompany(@RequestBody Company company) {
        return companyService.updateCompany(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
    }
}