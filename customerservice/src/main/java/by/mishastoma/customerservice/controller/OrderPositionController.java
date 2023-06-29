package by.mishastoma.customerservice.controller;

import by.mishastoma.customerservice.dto.OrderPositionRequest;
import by.mishastoma.customerservice.dto.OrderPositionResponse;
import by.mishastoma.customerservice.service.OrderPositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class OrderPositionController {

    private final OrderPositionService orderPositionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderPositionResponse createPosition(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                @RequestBody @Valid OrderPositionRequest request) {
        return orderPositionService.createPosition(authHeader, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePosition(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                               @PathVariable Long id) {
        orderPositionService.deleteOrderPosition(authHeader, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderPositionResponse updatePosition(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                @RequestBody @Valid OrderPositionRequest request,
                                                @PathVariable Long id) {
        return orderPositionService.updatePosition(authHeader, request, id);
    }

}
