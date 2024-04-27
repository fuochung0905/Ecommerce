package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReviewsRepository extends JpaRepository<Review, Long> {
}
