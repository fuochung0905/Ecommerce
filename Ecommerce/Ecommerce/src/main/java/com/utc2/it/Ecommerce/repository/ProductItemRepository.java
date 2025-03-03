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
    @Query("select pi from ProductItem  pi where pi.idColor=:colorId and pi.Id=:productItemId" )
    ProductItem findByColorId(@Param("colorId") Long colorId ,@Param("productItemId")Long productItemId);

    @Query("select pi from ProductItem pi where pi.product=:product and pi.deleted = FALSE")
    List<ProductItem>getAllProductItemByProductNoDelete(@Param("product") Product product);

    @Query("select pi from ProductItem pi where pi.Id=:productId and pi.deleted = FALSE ")
    ProductItem findByProductIdNoDelete(Long productId);

    @Query("select pi from ProductItem pi where pi.deleted = FALSE ")
    List<ProductItem> findByProductAllNoDelete();


}
