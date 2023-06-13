package by.mishastoma.authservice.repository;

import java.util.Optional;

public interface BaseAuthRepository<E, I> {
    Optional<E> findByUsername(String username);
}
