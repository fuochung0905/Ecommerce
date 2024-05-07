package com.utc2.it.Ecommerce.service;

public interface StatisticalService {
   Double getTotalAmountByProduct(Long productId);
   Double getTotalAmountMonth();
   Double getTotalAmountYear();
   Double getTotalAmountWeek();
   Integer quantityOrder();


}
