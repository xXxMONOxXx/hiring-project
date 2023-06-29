package by.mishastoma.companyservice.service;

import by.mishastoma.companyservice.dto.DecryptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-auth", url = "${auth.service.url}")
public interface CompanyAuthService {

    @GetMapping("/companies/decrypt?token={token}")
    DecryptResponse decryptToken(@PathVariable String token);

    @GetMapping("/companies/validate?token={token}")
    void validateToken(@PathVariable String token);
}
