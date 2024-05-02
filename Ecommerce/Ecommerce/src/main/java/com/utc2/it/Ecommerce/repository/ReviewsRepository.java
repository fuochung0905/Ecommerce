package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface ReviewsRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductId(Long productId);
}
