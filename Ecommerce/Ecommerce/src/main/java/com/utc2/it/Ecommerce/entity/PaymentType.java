package com.utc2.it.Ecommerce.entity;

import com.utc2.it.Ecommerce.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payment_type")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;
    private String image;
}
