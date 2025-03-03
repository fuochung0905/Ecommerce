package com.utc2.it.Ecommerce.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select c from Category c where c.categoryName=:categoryName and c.deleted = FALSE ")
    Category findByCategoryNameNoDelete(@Param("categoryName")String categoryName);
    @Query("select c from Category c where c.Id=:categoryId and c.deleted = FALSE ")

    Category findByCategoryIdNoDelete(@Param("categoryId")Long categoryId);

    @Query("select c from Category c where c.deleted = FALSE ")
    List<Category> findByCategoryAllNoDelete();
}
