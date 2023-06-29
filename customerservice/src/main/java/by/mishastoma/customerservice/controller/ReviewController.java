package by.mishastoma.customerservice.controller;

import by.mishastoma.customerservice.dto.ReviewRequest;
import by.mishastoma.customerservice.dto.ReviewResponse;
import by.mishastoma.customerservice.service.ReviewService;
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
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                      @RequestBody @Valid ReviewRequest request) {
        return reviewService.createReview(authHeader, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                            @PathVariable Long id) {
        reviewService.deleteReview(authHeader, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponse updateOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                      @PathVariable Long id,
                                      @RequestBody @Valid ReviewRequest request) {
        return reviewService.updateReview(authHeader, id, request);
    }
}
