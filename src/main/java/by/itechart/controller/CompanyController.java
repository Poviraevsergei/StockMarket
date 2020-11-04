package by.itechart.controller;

import lombok.RequiredArgsConstructor;
import by.itechart.model.domain.Company;
import by.itechart.service.CompanyService;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import by.itechart.exception.CustomException;
import org.springframework.http.ResponseEntity;
import by.itechart.model.response.CompanyResponse;
import by.itechart.model.response.CompanyNewsResponse;
import by.itechart.exception.ResourceNotFoundException;
import by.itechart.model.response.AllCompaniesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import by.itechart.service.serviceFeign.CompanyServiceFeign;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import by.itechart.model.response.CompanyFinancialReportResponse;

import java.util.List;

import static by.itechart.utils.ProjectProperties.COMPANY_NOT_FOUND;
import static by.itechart.utils.ProjectProperties.COMPANIES_NOT_FOUND;
import static by.itechart.utils.ProjectProperties.COMPANY_NEWS_NOT_FOUND;
import static by.itechart.utils.ProjectProperties.COMPANY_WAS_NOT_UPDATED;
import static by.itechart.utils.ProjectProperties.COMPANY_WAS_NOT_CREATED;
import static by.itechart.utils.ProjectProperties.COMPANY_REPORTS_NOT_FOUND;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    private final CompanyServiceFeign companyServiceFeign;
    private final CompanyService companyService;

    @GetMapping("/getCompanyFromDb/{ticker}")
    public ResponseEntity<Company> getCompanyByTicker(@PathVariable String ticker) {
        Company company = companyService.findCompanyFromDbByTicker(ticker)
                .orElseThrow(() -> new ResourceNotFoundException(COMPANY_NOT_FOUND));
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("/getAllCompaniesFromDb")
    public ResponseEntity<List<Company>> getAllCompaniesFromDb() {
        List<Company> companies = companyService.findAllCompanyFromDb()
                .orElseThrow(() -> new ResourceNotFoundException(COMPANIES_NOT_FOUND));
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/getAllCompanies")
    public ResponseEntity<List<AllCompaniesResponse>> getAllCompanies() {
        List<AllCompaniesResponse> companies = companyServiceFeign.getAllCompanies()
                .orElseThrow(() -> new ResourceNotFoundException(COMPANY_NOT_FOUND));
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/getCompany/{ticker}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable String ticker) {
        CompanyResponse company = companyServiceFeign.getCompany(ticker)
                .orElseThrow(() -> new ResourceNotFoundException(COMPANY_NOT_FOUND));
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("/getCompanyNews")
    public ResponseEntity<List<CompanyNewsResponse>> getCompanyNews(String ticker, String from, String to) {
        List<CompanyNewsResponse> companyNews = companyServiceFeign.getCompanyNews(ticker, from, to)
                .orElseThrow(() -> new ResourceNotFoundException(COMPANY_NEWS_NOT_FOUND));
        return new ResponseEntity<>(companyNews, HttpStatus.OK);
    }

    @GetMapping("/getFinancialReport/{ticker}")
    public ResponseEntity<CompanyFinancialReportResponse> getFinancialReport(@PathVariable String ticker) {
        CompanyFinancialReportResponse financialReport = companyServiceFeign.getFinancialReport(ticker)
                .orElseThrow(() -> new ResourceNotFoundException(COMPANY_REPORTS_NOT_FOUND));
        return new ResponseEntity<>(financialReport, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id) {
        Company company = companyService.findCompanyById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMPANY_NOT_FOUND));
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company resultCompany = companyService.createCompany(company);
        if (resultCompany == null) {
            throw new CustomException(COMPANY_WAS_NOT_CREATED);
        }
        return new ResponseEntity<>(resultCompany, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
        Company resultCompany = companyService.updateCompany(company);
        if (resultCompany == null) {
            throw new CustomException(COMPANY_WAS_NOT_UPDATED);
        }
        return new ResponseEntity<>(resultCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
