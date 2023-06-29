package by.mishastoma.companyservice.repository;

import by.mishastoma.companyservice.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByCompanyIdAndOrderPositionId(Long companyId, Long orderPositionId);
}
