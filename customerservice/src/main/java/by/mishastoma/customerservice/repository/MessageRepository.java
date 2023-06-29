package by.mishastoma.customerservice.repository;

import by.mishastoma.customerservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByApplicationId(Long applicationId);
}
