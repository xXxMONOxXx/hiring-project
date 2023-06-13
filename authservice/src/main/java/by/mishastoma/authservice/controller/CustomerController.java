package by.mishastoma.authservice.controller;

import by.mishastoma.authservice.dto.CustomerRequest;
import by.mishastoma.authservice.dto.CustomerResponse;
import by.mishastoma.authservice.dto.TokenResponse;
import by.mishastoma.authservice.service.CustomerService;
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
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse register(@RequestBody @Valid CustomerRequest request) {
        return customerService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody @Valid CustomerRequest request) {
        return customerService.login(request);
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public void validateToken(@RequestParam("token") String token) {
        jwtTokenUtil.validateJwtToken(token);
    }

    @GetMapping("/decrypt")
    @ResponseStatus(HttpStatus.OK)
    public String decryptToken(@RequestParam("token") String token) {
        return jwtTokenUtil.getUsernameFromJwtToken(token);
    }

}
