package by.mishastoma.companyservice.controller;

import by.mishastoma.companyservice.dto.ApplicationRequest;
import by.mishastoma.companyservice.dto.ApplicationResponse;
import by.mishastoma.companyservice.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                      @RequestBody @Valid ApplicationRequest request){
        return applicationService.create(authHeader, request);
    }


}
