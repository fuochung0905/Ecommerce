package com.utc2.it.Ecommerce.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select c from Category c where c.categoryName=:categoryName")
   Category findByCategoryName(@Param("categoryName")String categoryName);
}
