package com.utc2.it.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
