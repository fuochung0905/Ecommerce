package com.utc2.it.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.ShoppingCart;
import com.utc2.it.Ecommerce.entity.User;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    @Query("select sc from ShoppingCart sc where sc.user=:user")
    ShoppingCart findShoppingCartByUser(@Param("user") User user);

    @Query("SELECT sc FROM ShoppingCart sc JOIN FETCH sc.cartDetails cd JOIN FETCH cd.productItem  WHERE sc.user.email = :userName")
    ShoppingCart findShoppingCartByUserWithProduct(@Param("userName")String userName);
}
