package by.itechart.service;

import by.itechart.model.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Optional<List<Company>> findAllCompanyFromDb();

    Optional<Company> findCompanyFromDbByTicker(String ticker);

    Optional<Company> findCompanyById(Long id);

    Company createCompany(Company company);

    Company updateCompany(Company company);

    void deleteCompanyById(Long id);
}