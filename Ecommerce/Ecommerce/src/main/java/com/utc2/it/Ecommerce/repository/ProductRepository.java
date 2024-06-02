package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.Category;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategory(Category category);
    @Query("select  p from Product p where p.productName LIKE %:productName% ")
    List<Product>findAllBySearchLikeProductName(@Param("productName") String productName);
}
