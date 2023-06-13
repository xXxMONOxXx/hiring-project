package by.mishastoma.authservice.repository;

import by.mishastoma.authservice.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends BaseAuthRepository<Company, Long>, JpaRepository<Company, Long> {
}
