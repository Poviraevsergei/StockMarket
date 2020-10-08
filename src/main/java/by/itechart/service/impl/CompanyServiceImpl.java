package by.itechart.service.impl;

import lombok.RequiredArgsConstructor;
import by.itechart.model.domain.Company;
import by.itechart.service.CompanyService;
import org.springframework.stereotype.Service;
import by.itechart.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

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
        company.setId(companyRepository.findCompaniesByTicker(company.getTicker()).getId());
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }
}