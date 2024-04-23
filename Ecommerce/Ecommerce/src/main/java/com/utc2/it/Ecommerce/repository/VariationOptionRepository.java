package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.entity.ProductItem;
import com.utc2.it.Ecommerce.entity.Variation;
import com.utc2.it.Ecommerce.entity.VariationOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariationOptionRepository extends JpaRepository<VariationOption,Long> {
//    @Query("select vo from VariationOption  vo where vo.productItems=:productItem")
//    List<VariationOption>getAllVariationOptionByProduct(@Param("productItem") ProductItem productItem);
    @Query("select vo from VariationOption  vo where vo.variation=:variation")
    List<VariationOption>getAllVariationByVariation(@Param("variation")Variation variation);

    @Query("SELECT pivo.variationOption FROM ProductItemVariationOption pivo WHERE pivo.productItem = :productItem")
    List<VariationOption> findVariationOptionsByProductItem(@Param("productItem") ProductItem productItem);
}
