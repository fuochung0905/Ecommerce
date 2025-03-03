package com.utc2.it.Ecommerce.Base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private String deleteBy;
    private Date deleteAt;
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}
