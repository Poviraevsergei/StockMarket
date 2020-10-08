package by.itechart.repository;

import by.itechart.model.domain.Company;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Company findCompaniesByTicker(String ticker);
}