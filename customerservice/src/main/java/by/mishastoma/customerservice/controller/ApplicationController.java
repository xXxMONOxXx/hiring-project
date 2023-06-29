package by.mishastoma.customerservice.controller;

import by.mishastoma.customerservice.dto.ApplicationRequest;
import by.mishastoma.customerservice.dto.ApplicationResponse;
import by.mishastoma.customerservice.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                      @RequestBody @Valid ApplicationRequest request,
                                      @PathVariable Long id) {
        return applicationService.updateApplication(authHeader, request, id);
    }
}
