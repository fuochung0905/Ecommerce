package com.utc2.it.Ecommerce.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopCustomerDTO {
    private Long customerId;
    private String customerName;
    private Double totalSpent;

}
