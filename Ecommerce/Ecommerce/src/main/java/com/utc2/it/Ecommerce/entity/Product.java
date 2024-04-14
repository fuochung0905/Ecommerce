package com.utc2.it.Ecommerce.entity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotNull
    private String productName;
    @NotNull
    private  String description;
    private double importPrice;
    private double exportPrice;
    private int quantity;
    private int qyt_stock;
    private  int sold;
    @Column(name = "image_name")
    private String imageName;
    //    @Lob
//    @Column(columnDefinition = "longblob")
//    private byte[] image;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<CartDetail>cartDetails= new ArrayList<>();
    @ManyToMany()
    @JoinTable(name = "product_variation",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "variation_id"))
    private Set<Variation> variations= new HashSet<>();
//    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
//    private List<ShoppingCartItem>shoppingCartItems=new ArrayList<>() ;
}
