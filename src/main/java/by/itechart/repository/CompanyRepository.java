package by.itechart.repository;

import by.itechart.model.domain.Company;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Optional<Company> findCompaniesByTicker(String ticker);
}