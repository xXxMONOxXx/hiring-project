package by.mishastoma.customerservice.controller;

import by.mishastoma.customerservice.dto.CustomerRequest;
import by.mishastoma.customerservice.dto.CustomerResponse;
import by.mishastoma.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                   @RequestBody @Valid CustomerRequest request) {
        return customerService.update(authHeader, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse get(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return customerService.get(authHeader);
    }
}
