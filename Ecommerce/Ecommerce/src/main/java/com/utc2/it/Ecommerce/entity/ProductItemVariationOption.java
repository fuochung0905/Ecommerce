package com.utc2.it.Ecommerce.entity;

import com.utc2.it.Ecommerce.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_item_varition_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductItemVariationOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thuộc tính quantity
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id")
    private ProductItem productItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_option_id")
    private VariationOption variationOption;
}
