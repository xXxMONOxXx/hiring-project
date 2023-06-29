package by.mishastoma.companyservice.util;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUtil {

    public Optional<String> getTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}
