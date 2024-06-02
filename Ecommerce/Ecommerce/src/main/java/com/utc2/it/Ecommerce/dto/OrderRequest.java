package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long cartid;
    private String message;
    private Long paymentId;
    private Long paymentTypeId;
    private Long deliveryId;
}
