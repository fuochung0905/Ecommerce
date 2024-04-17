package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Category;
import com.utc2.it.Ecommerce.entity.VariationOption;

import java.util.List;

@Repository
public interface VariationRepository extends JpaRepository<Variation,Long> {
    @Query("select v from Variation v where v.category=:category")
    List<VariationOption> getListVariationByCategory(@Param("category") Category category);

}
