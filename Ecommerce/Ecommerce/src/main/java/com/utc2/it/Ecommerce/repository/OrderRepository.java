package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.dto.TopCustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Order;
import com.utc2.it.Ecommerce.entity.User;
import com.utc2.it.Ecommerce.dto.TopCustomerDTO;
import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where o.user=:user ")
    List<Order> findAllOrderByUserWithOrderAll(@Param("user")User user);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where o.user=:user and o.isOrdered =:active" )
    List<Order> findAllOrderByUserWithOrderBOrderByOrdered(@Param("user")User user,@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where o.user=:user and o.isApproved=:active")
    List<Order> findAllOrderByUserWithOrderApproved(@Param("user")User user,@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where o.user=:user and o.isTransport=:active")
    List<Order> findAllOrderByUserWithOrderTransport(@Param("user")User user,@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where o.user=:user and o.isDelivered=:active")
    List<Order> findAllOrderByUserWithOrderDelivered(@Param("user")User user,@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where o.user=:user and o.isCancel=:active")
    List<Order> findAllOrderByUserWithOrderCancel(@Param("user")User user,@Param("active")Boolean active);


    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem ")
    List<Order> findAllOrderWithOrderAll();
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where o.isOrdered =:active" )
    List<Order> findAllOrderWithOrderBOrderByOrdered(@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where  o.isApproved=:active")
    List<Order> findAllOrderWithOrderApproved(@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where   o.isTransport=:active")
    List<Order> findAllOrderWithOrderTransport(@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where o.isDelivered=:active")
    List<Order> findAllOrderWithOrderDelivered(@Param("active")Boolean active);
    @Query("select o from Order o join fetch o.orderDetails od join fetch od.productItem where  o.isCancel=:active")
    List<Order> findAllOrderWithOrderCancel(@Param("active")Boolean active);
    @Query("select sum(o.totalPrice) from Order o where o.user=:user")
    Double getTotalAmount(@Param("user")User user);
    @Query("select count (o) from  Order  o where o.isOrdered=:active")
    Integer getCountApproval(@Param("active")boolean active);
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE DATE(o.createDate) = DATE(:currentDate) AND o.isDelivered = :active")
    Double getTotalRevenueToday(@Param("currentDate") LocalDateTime currentDate, @Param("active") boolean active);
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE MONTH(o.createDate) = :month AND YEAR(o.createDate) = :year AND o.isDelivered = :active")
    Double getTotalRevenueOfMonth(@Param("month") int month, @Param("year") int year, @Param("active") boolean active);

}
