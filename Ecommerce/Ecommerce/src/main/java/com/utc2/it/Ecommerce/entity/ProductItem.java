package com.utc2.it.Ecommerce.entity;

import jakarta.persistence.*;
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

    private String productItemImage;
    private double price;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @OneToOne(mappedBy = "productItem")
    private OrderDetail orderDetail;
    @OneToMany(mappedBy = "productItem",cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails= new ArrayList<>();
    @ManyToMany()
    @JoinTable(name = "productItem_variation",
            joinColumns = @JoinColumn(name = "productItem_id"),
            inverseJoinColumns = @JoinColumn(name = "variation_id"))
    private Set<VariationOption> variations= new HashSet<>();

}
