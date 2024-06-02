package com.utc2.it.Ecommerce.service;

public interface StatisticService {
    Double getTotalRevenueToday();
    Double getTotalRevenueOfMonth(int month, int year, boolean active);
}
