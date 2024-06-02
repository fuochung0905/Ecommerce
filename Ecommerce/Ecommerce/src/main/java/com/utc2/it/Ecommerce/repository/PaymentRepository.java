package com.utc2.it.Ecommerce.repository;

import com.utc2.it.Ecommerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findPaymentByName(final String name);
}
