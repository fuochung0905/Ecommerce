package com.utc2.it.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
