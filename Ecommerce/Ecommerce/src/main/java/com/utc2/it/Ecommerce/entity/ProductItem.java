package com.utc2.it.Ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "product_Item")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {
    @Column(name = "productItem_id")
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private int qyt_stock;
    private Long idColor;
    private String productItemImage;
    @Min(value = 0, message = "Giá tiền không được âm")
    private double price;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @OneToMany(mappedBy = "productItem")
    private List<OrderDetail> orderDetails= new ArrayList<>();

    @OneToMany(mappedBy = "productItem")
    private List<CartDetail> cartDetails= new ArrayList<>();


    @OneToMany(mappedBy = "productItem",cascade = CascadeType.ALL)
    private List<ProductItemVariationOption> productItemVariationOptions= new ArrayList<>();


}
