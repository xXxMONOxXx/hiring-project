package by.mishastoma.customerservice.controller;

import by.mishastoma.customerservice.dto.OrderRequest;
import by.mishastoma.customerservice.dto.OrderResponse;
import by.mishastoma.customerservice.dto.StatusResponse;
import by.mishastoma.customerservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/statuses")
    @ResponseStatus(HttpStatus.OK)
    public List<StatusResponse> getAllOrderStatuses() {
        return orderService.getAllStatuses();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                     @RequestBody @Valid OrderRequest request) {
        return orderService.createOrder(request, authHeader);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                            @PathVariable Long id) {
        orderService.deleteOrder(authHeader, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse updateOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                     @PathVariable Long id,
                                     @RequestBody @Valid OrderRequest request) {
        return orderService.update(authHeader, id, request);
    }
}
