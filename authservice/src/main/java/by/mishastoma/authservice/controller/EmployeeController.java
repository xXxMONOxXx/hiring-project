package by.mishastoma.authservice.controller;

import by.mishastoma.authservice.dto.EmployeeRequest;
import by.mishastoma.authservice.dto.EmployeeResponse;
import by.mishastoma.authservice.dto.TokenResponse;
import by.mishastoma.authservice.service.EmployeeService;
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
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse register(@RequestBody @Valid EmployeeRequest request) {
        return employeeService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody @Valid EmployeeRequest request) {
        return employeeService.login(request);
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
