package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Long orderId;
    private String comment;
    private int rating;
    private String variation;
    private String date;
    private Long productId;
    private Long userId;
    private String imageUser;
    private String productName;
    private String imageName;
    private String username;
}
