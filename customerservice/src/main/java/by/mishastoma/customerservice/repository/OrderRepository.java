package by.mishastoma.customerservice.repository;

import by.mishastoma.customerservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByCustomerIdAndPositions_Applications_Id(Long customerId, Long applicationId);
}
