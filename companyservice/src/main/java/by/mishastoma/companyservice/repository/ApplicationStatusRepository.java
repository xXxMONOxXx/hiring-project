package by.mishastoma.companyservice.repository;

import by.mishastoma.companyservice.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
}
