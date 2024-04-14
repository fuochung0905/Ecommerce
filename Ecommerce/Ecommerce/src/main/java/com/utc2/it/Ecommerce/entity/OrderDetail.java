package com.utc2.it.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="order_detail")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @jakarta.persistence.Id
    private Long Id;
    private int quantity;
    private String size;
    private String color;
    private double price;
    private String addressUser;
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "fk_order_id")
     private Order order;
     @OneToOne
     private Product product;

}
