package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.ReviewDto;
import com.utc2.it.Ecommerce.entity.Review;

import java.util.List;

public interface ReviewService {
    Long createReview(ReviewDto reviewDto);
    void saveReviewImage(Long reviewId, String imageName);
    String deleteReview(Long reviewId);
    List<ReviewDto> getAllReviewsByProductId(Long productId);
}
