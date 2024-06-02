package com.utc2.it.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_detail")
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_detail_id")
    private Long Id;
    private int quantity;
    private double price;
    private Long idColor;
    private Long idSize;
    private String color;
    private String size;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductItem productItem;
    @ManyToOne(fetch = FetchType.LAZY)
    private ShoppingCart shopping_cart;
}