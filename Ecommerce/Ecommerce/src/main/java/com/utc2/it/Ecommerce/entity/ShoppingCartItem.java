package com.utc2.it.Ecommerce.entity;

import com.utc2.it.Ecommerce.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ShoppingCartItem extends BaseEntity {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
    private String size;
    private String color;
    private String productName;
    private double price;
    private String image;
}

