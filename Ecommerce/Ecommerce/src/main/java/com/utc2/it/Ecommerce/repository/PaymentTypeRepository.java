package com.utc2.it.Ecommerce.repository;


import com.utc2.it.Ecommerce.entity.Payment;
import com.utc2.it.Ecommerce.entity.PaymentType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {
    @Query("select pt from PaymentType  pt where pt.payment=:payment")
    List<PaymentType>getPaymentTypeByPayment(@Param("payment")Payment payment);
}
