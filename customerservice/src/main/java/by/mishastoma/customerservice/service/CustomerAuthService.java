package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.DecryptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-auth", url = "${auth.service.url}")
public interface CustomerAuthService {

    @GetMapping("/customers/decrypt?token={token}")
    DecryptResponse decryptToken(@PathVariable String token);

    @GetMapping("/customers/validate?token={token}")
    void validateToken(@PathVariable String token);
}
