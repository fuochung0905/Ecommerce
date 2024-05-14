package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.ReviewDto;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.exception.NotFoundException;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReviewServiceImpl implements ReviewService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderDetailRepository orderDetailRepository;
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
    public Long createReview(ReviewDto reviewDto) {
        OrderDetail findorderDetail=orderDetailRepository.findById(reviewDto.getOrderId()).orElseThrow();
        ProductItem productItem = findorderDetail.getProductItem();
        Product product=productItem.getProduct();
       String username = getCurrentUsername();
       User user = getUser(username);
        List<Order> orders=orderRepository.findAllOrderByUserWithOrderDelivered(user,true);
        for(Order order:orders){
            for (OrderDetail orderDetail:order.getOrderDetails()){
                if(orderDetail.getProductItem().getProduct().getId().equals(product.getId())){
                    Review review=new Review();
                    review.setProduct(product);
                    review.setUser(user);
                    review.setVariation(orderDetail.getSize()+" "+orderDetail.getColor());
                    review.setComment(reviewDto.getComment());
                    review.setRating(reviewDto.getRating());
                    LocalDateTime currentTime = LocalDateTime.now();
                    review.setDate(currentTime);
                    Review save=reviewsRepository.save(review);
                    ReviewDto dto=new ReviewDto();
                    dto.setComment(save.getComment());
                    dto.setVariation(review.getVariation());
                    dto.setRating(save.getRating());
                    dto.setDate(save.getDate().toString());
                    dto.setProductId(product.getId());
                    dto.setUserId(save.getUser().getId());
                    return save.getId();
                }
            }
        }
       return null;
    }

    @Override
    public void saveReviewImage(Long reviewId, String imageName) {
        Review review=reviewsRepository.findById(reviewId).orElseThrow(()->new NotFoundException("Not found review "));
        review.setImage(imageName);
        reviewsRepository.save(review);
    }

    @Override
    public String deleteReview(Long reviewId) {
        return "";
    }

    @Override
    public List<ReviewDto> getAllReviewsByProductId(Long productId) {
       List<Review> reviews=reviewsRepository.findAllByProductId(productId);
       List<ReviewDto> dtos=new ArrayList<>();
       for(Review review:reviews){
           Product product=productRepository.findById(review.getProduct().getId()).orElseThrow();
           User user=userRepository.findById(review.getUser().getId()).orElseThrow();
           ReviewDto dto=new ReviewDto();
           dto.setUsername(user.getUsername());
           dto.setProductName(product.getProductName());
           dto.setImageName(product.getImageName());
           dto.setComment(review.getComment());
           dto.setImageUser(user.getImage());
           dto.setVariation(review.getVariation());
           dto.setRating(review.getRating());
           dto.setImageReview(review.getImage());
           dto.setDate(review.getDate().toString());
           dto.setProductId(review.getProduct().getId());
           dto.setUserId(review.getUser().getId());
           dtos.add(dto);
       }
       return dtos;
    }

    @Override
    public Double sumRatingByProductId(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        Double result=reviewsRepository.sumRatingByProductId(product);
        return result;
    }

    @Override
    public Integer getReviewCountByProductId(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        Integer result=reviewsRepository.countRatingByProductId(product);
        return result;
    }
}
