package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaymentTypeDto {
    private Long id;
    private String value;
    private Long paymentId;
    private String image;
}
