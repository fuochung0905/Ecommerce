package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.ReviewDto;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.OrderRepository;
import com.utc2.it.Ecommerce.repository.ProductRepository;
import com.utc2.it.Ecommerce.repository.ReviewsRepository;
import com.utc2.it.Ecommerce.repository.UserRepository;
import com.utc2.it.Ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReviewServiceImpl implements ReviewService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewsRepository reviewsRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
    private User getUser(String username){
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Trả về tên người dùng hiện đang đăng nhập
    }
    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        Product product = productRepository.findById(reviewDto.getProductId()).orElseThrow();
       String username = getCurrentUsername();
       User user = getUser(username);
        List<Order> orders=orderRepository.findAllOrderByUserWithOrderDelivered(user,true);
        for(Order order:orders){
            for (OrderDetail orderDetail:order.getOrderDetails()){
                if(orderDetail.getProductItem().getProduct().getId().equals(product.getId())){
                    Review review=new Review();
                    review.setProduct(product);
                    review.setUser(user);
                    review.setComment(reviewDto.getComment());
                    review.setRating(reviewDto.getRating());
                    LocalDateTime currentTime = LocalDateTime.now();
                    review.setDate(currentTime);
                    Review save=reviewsRepository.save(review);
                    ReviewDto dto=new ReviewDto();
                    dto.setComment(save.getComment());
                    dto.setRating(save.getRating());
                    dto.setDate(save.getDate());
                    dto.setProductId(product.getId());
                    dto.setUserId(save.getUser().getId());
                    return dto;
                }
            }
        }
       return null;
    }

    @Override
    public String deleteReview(Long reviewId) {
        return "";
    }
}
