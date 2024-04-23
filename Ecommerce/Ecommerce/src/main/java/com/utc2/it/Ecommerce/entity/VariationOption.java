package com.utc2.it.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variation_option")
public class VariationOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_variation_id")
    private Variation variation;

    @OneToMany(mappedBy = "variationOption",cascade = CascadeType.ALL)
    private List<ProductItemVariationOption> productItemVariationOptions= new ArrayList<>();

}
