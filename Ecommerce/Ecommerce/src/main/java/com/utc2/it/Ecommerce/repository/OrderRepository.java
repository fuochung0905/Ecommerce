package com.utc2.it.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Order;
import com.utc2.it.Ecommerce.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select o from Order  o where o.user=:user")
    Order findOrderByUser(@Param("user")User user);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.product where o.user=:user and o.isOrdered=:active")
    List<Order> findAllOrderByUserWithOrderSuccess(@Param("user")User user,@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.product where o.user=:user and o.isApproved=:active")
    List<Order> findAllOrderByUserWithOrderApproved(@Param("user")User user,@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.product where o.user=:user and o.isTransport=:active")
    List<Order> findAllOrderByUserWithOrderTransport(@Param("user")User user,@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.product where o.user=:user and o.isDelivered=:active")
    List<Order> findAllOrderByUserWithOrderDelivered(@Param("user")User user,@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.product where o.user=:user and o.isCancel=:active")
    List<Order> findAllOrderByUserWithOrderCancel(@Param("user")User user,@Param("active")Boolean active);
}
