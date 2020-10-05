package by.itechart.repository;

import by.itechart.model.domain.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Company findCompaniesByTicker(String ticker);
}