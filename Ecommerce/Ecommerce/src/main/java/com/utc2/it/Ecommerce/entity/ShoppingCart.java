package com.utc2.it.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long Id;
    private boolean isSold;
    @OneToMany(mappedBy = "shopping_cart",cascade = CascadeType.ALL)
    private List<CartDetail>cartDetails=new ArrayList<>() ;
    @OneToOne
    private User user;



}
