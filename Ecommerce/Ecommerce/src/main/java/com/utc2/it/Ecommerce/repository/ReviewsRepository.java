package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface ReviewsRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductId(Long productId);

    @Query("select sum(r.rating) from Review r where r.product=:product")
    Double sumRatingByProductId(@Param("product") Product product);
    @Query("select count(r) from Review  r where r.product=:product")
    Integer countRatingByProductId(@Param("product") Product product);
}
