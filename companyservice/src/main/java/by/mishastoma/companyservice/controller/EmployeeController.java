package by.mishastoma.companyservice.controller;


import by.mishastoma.companyservice.dto.EmployeeRequest;
import by.mishastoma.companyservice.dto.EmployeeResponse;
import by.mishastoma.companyservice.model.Employee;
import by.mishastoma.companyservice.service.EmployeeService;
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
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                   @RequestBody EmployeeRequest request){
        return employeeService.update(authHeader, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse get(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        return employeeService.get(authHeader);
    }
}
