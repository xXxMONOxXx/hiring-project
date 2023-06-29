package by.mishastoma.companyservice.controller;


import by.mishastoma.companyservice.dto.CompanyEmployeeRequest;
import by.mishastoma.companyservice.dto.CompanyEmployeeResponse;
import by.mishastoma.companyservice.service.CompanyEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-employees")
public class CompanyEmployeeController {

    private final CompanyEmployeeService companyEmployeeService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyEmployeeResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                          @RequestBody CompanyEmployeeRequest request){
        return companyEmployeeService.update(authHeader, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyEmployeeResponse create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                          @RequestBody CompanyEmployeeRequest request){
        return companyEmployeeService.create(authHeader, request);
    }


}
