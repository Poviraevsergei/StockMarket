package by.itechart.service.impl;

import by.itechart.model.domain.Company;
import by.itechart.repository.CompanyRepository;
import by.itechart.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> findAllCompanyFromDb() {
        return (List<Company>) companyRepository.findAll();
    }

    @Override
    public Company findCompanyFromDbByTicker(String ticker) {
        return companyRepository.findCompaniesByTicker(ticker);
    }

    @Override
    public Optional<Company> findCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompanyById(Long id) {

        companyRepository.deleteById(id);
    }
}