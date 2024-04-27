package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.ReviewDto;
import com.utc2.it.Ecommerce.entity.Review;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);
    String deleteReview(Long reviewId);
}
