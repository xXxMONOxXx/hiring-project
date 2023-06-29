package by.mishastoma.companyservice.repository;

import by.mishastoma.companyservice.model.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}
