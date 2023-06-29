package by.mishastoma.authservice.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CustomUserDetails extends UserDetails {
    Long getId();

    List<String> getUserAuthoritiesAsStringList();
}
