package by.mishastoma.companyservice.repository;

import by.mishastoma.companyservice.model.CompanyEmployee;
import by.mishastoma.companyservice.model.CompanyEmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployee, CompanyEmployeeId> {

    boolean existsByCompanyIdAndEmployeeId(Long companyId, Long employeeId);

    Optional<CompanyEmployee> findByCompanyIdAndEmployeeId(Long companyId, Long employeeId);
}
