package by.mishastoma.companyservice.service;

import by.mishastoma.companyservice.dto.DecryptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-auth", url = "${auth.service.url}")
public interface EmployeeAuthService {

    @GetMapping("/employees/decrypt?token={token}")
    DecryptResponse decryptToken(@PathVariable String token);

    @GetMapping("/employees/validate?token={token}")
    void validateToken(@PathVariable String token);
}
