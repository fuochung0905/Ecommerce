package com.utc2.it.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Order;
import com.utc2.it.Ecommerce.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    @Query("select od from OrderDetail od join fetch od.product p where od.order=:order")
    List<OrderDetail> findOrderDetailByOrder(@Param("order")Order order);

}
