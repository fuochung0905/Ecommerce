package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.entity.ProductItem;
import com.utc2.it.Ecommerce.entity.VariationOption;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
    @Query("select pi from ProductItem  pi where pi.idColor=:colorId")
    ProductItem findByColorId(@Param("colorId") Long colorId);
//   @Query("select pi from ProductItem  pi " +
////           "join pi.variations vo " +
//           "where " +
////           "vo in :variationOptions" +
////           " and " +
//           "pi.product=:product")
//    ProductItem getProductItemByProductAndVariations(@Param("variationOptions")List<VariationOption>variationOptions, @Param("product")Product product);
}
