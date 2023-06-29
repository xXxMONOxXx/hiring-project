package by.mishastoma.companyservice.controller;


import by.mishastoma.companyservice.dto.CompanyRequest;
import by.mishastoma.companyservice.dto.CompanyResponse;
import by.mishastoma.companyservice.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                  @RequestBody @Valid CompanyRequest request){
        return companyService.update(authHeader, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyResponse get(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        return companyService.get(authHeader);
    }
}
