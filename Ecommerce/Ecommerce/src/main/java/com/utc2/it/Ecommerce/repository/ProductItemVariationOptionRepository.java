package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.ProductItem;
import com.utc2.it.Ecommerce.entity.ProductItemVariationOption;
import com.utc2.it.Ecommerce.entity.VariationOption;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProductItemVariationOptionRepository extends JpaRepository<ProductItemVariationOption, Long> {
@Query("select pivo from ProductItemVariationOption  pivo where pivo.productItem =:productItem")
    List<ProductItemVariationOption> findAllByProductItemId(@Param("productItem") ProductItem productItem);
   @Query("select pivo from ProductItemVariationOption pivo where pivo.productItem=:productItem and pivo.variationOption=:variationOption")
    ProductItemVariationOption findProductItemVariationOptionByProductItemAndVariationOption(@Param("productItem") ProductItem productItem,@Param("variationOption")VariationOption variationOption);
}
