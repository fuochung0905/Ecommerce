package com.utc2.it.Ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_Id")
    private Long Id;
    private double totalPrice;

    @NotNull
    private LocalDateTime createDate;
    @NotNull
    private LocalDateTime updateDate;
    private boolean isOrdered;
    @NotNull
    private boolean isApproved;
    @NotNull
    private boolean isTransport;
    @NotNull
    private boolean isDelivered;
    @NotNull
    private boolean isCancel;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails= new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private User user;

}
