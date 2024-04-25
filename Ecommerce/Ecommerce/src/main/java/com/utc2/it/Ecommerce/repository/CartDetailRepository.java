package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Long> {
    @Query("select count (*) from ShoppingCart sc join CartDetail  cd on cd.shopping_cart=sc where sc.user=:user")
    Integer GetCartItemCount(@Param("user")User user);

    @Query("select cd from CartDetail cd where cd.shopping_cart=:shoppingCart and cd.productItem=:productItem ")
    List<CartDetail> findCartDetailByShopping_cartAndProduct(@Param("shoppingCart") ShoppingCart shoppingCart
            ,@Param("productItem") ProductItem productItem);


    @Query("select cd from CartDetail cd where cd.shopping_cart=:shoppingCart")
    List<CartDetail>findListCartDetailByShoppingCart(@Param("shoppingCart")ShoppingCart shoppingCart);


}
