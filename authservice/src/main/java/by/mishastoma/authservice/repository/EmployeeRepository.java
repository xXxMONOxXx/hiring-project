package by.mishastoma.authservice.repository;

import by.mishastoma.authservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseAuthRepository<Employee, Long>, JpaRepository<Employee, Long> {
}
