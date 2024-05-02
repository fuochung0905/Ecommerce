package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ReviewDto;
import com.utc2.it.Ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/review")

public class UReviewController {
    private final ReviewService reviewService;
    @PostMapping("/createNewReview")
    public ResponseEntity<String> createNewReview(@RequestBody ReviewDto dto) {
        ReviewDto reviewDto= reviewService.createReview(dto);
        if(reviewDto==null){
            return ResponseEntity.badRequest().body("Only users who have ordered this product can comment");
        }
        return ResponseEntity.ok("Add successful review");
    }
    @GetMapping("product/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewById(@PathVariable Long productId) {
        List<ReviewDto>getAllReviewByProduct=reviewService.getAllReviewsByProductId(productId);
        return new ResponseEntity<>(getAllReviewByProduct, HttpStatus.OK);
    }
}
