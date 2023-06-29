package by.mishastoma.authservice.controller;

import by.mishastoma.authservice.config.RoleConfig;
import by.mishastoma.authservice.dto.CompanyRequest;
import by.mishastoma.authservice.dto.CompanyResponse;
import by.mishastoma.authservice.dto.DecryptResponse;
import by.mishastoma.authservice.dto.LoginRequest;
import by.mishastoma.authservice.dto.TokenResponse;
import by.mishastoma.authservice.service.CompanyService;
import by.mishastoma.authservice.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse register(@RequestBody @Valid CompanyRequest request) {
        return companyService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return companyService.login(request);
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public void validateToken(@RequestParam("token") String token) {
        jwtTokenUtil.validateJwtToken(token, RoleConfig.getCOMPANY_ROLE());
    }

    @GetMapping("/decrypt")
    @ResponseStatus(HttpStatus.OK)
    public DecryptResponse decryptToken(@RequestParam("token") String token) {
        return companyService.decrypt(token);
    }
}
