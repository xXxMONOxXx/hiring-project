package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.DecryptResponse;
import by.mishastoma.customerservice.dto.ReviewRequest;
import by.mishastoma.customerservice.dto.ReviewResponse;
import by.mishastoma.customerservice.exception.ForbiddenException;
import by.mishastoma.customerservice.exception.ReviewNotFoundException;
import by.mishastoma.customerservice.mapper.EntityMapper;
import by.mishastoma.customerservice.model.Review;
import by.mishastoma.customerservice.repository.CustomerRepository;
import by.mishastoma.customerservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ModelMapper modelMapper;
    private final EntityMapper entityMapper;
    private final ReviewRepository reviewRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public ReviewResponse createReview(String authHeader, ReviewRequest request) {
        DecryptResponse decryptResponse = customerService.decryptAuthHeader(authHeader);
        Review review = modelMapper.map(request, Review.class);
        review.setCustomerId(decryptResponse.getId());
        return modelMapper.map(reviewRepository.save(review), ReviewResponse.class);
    }

    public ReviewResponse updateReview(String authHeader, Long reviewId, ReviewRequest request) {
        Review review = customerIsAllowedToChangeReview(authHeader, reviewId);
        Review updatedReview = entityMapper.updateReview(review, request);
        return modelMapper.map(updatedReview, ReviewResponse.class);
    }

    public void deleteReview(String authHeader, Long reviewId) {
        customerIsAllowedToChangeReview(authHeader, reviewId);
        reviewRepository.deleteById(reviewId);
    }

    private Review customerIsAllowedToChangeReview(String authHeader, Long reviewId) {
        DecryptResponse decryptResponse = customerService.decryptAuthHeader(authHeader);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(String.format("Review %d wasn't found", reviewId)));
        if (!decryptResponse.getId().equals(review.getCustomerId())) {
            throw new ForbiddenException("Users ids are different, access denied");
        } else {
            return review;
        }
    }
}
