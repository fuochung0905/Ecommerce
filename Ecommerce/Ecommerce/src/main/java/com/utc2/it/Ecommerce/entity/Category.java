package com.utc2.it.Ecommerce.entity;

import com.utc2.it.Ecommerce.Base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotBlank(message = "Tên loại sản phẩm không được để trống")
    private String categoryName;
    @OneToMany(mappedBy = "category")
    private List<Product>products= new ArrayList<>();
    @OneToMany(mappedBy = "category")
    private List<Variation>variations= new LinkedList<>();

}
