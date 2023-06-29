package by.mishastoma.authservice.repository;

import by.mishastoma.authservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseAuthRepository<Customer, Long>, JpaRepository<Customer, Long> {

}
