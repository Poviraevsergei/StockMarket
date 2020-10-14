package by.itechart.security.repository;

import by.itechart.model.domain.Security;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends CrudRepository<Security, Long> {
    Security findByLogin(String login);
}
