package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.repository.OrderRepository;
import com.utc2.it.Ecommerce.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class StatisticServiceImpl implements StatisticService {
    private final OrderRepository orderRepository;
    @Override
    public Double getTotalRevenueToday() {
        LocalDateTime currentDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        Double revenue = orderRepository.getTotalRevenueToday(currentDate, true); // True or false based on isDelivered
       if(revenue==null){
           return 0.0;
       }
       return revenue;
    }

    @Override
    public Double getTotalRevenueOfMonth(int month, int year, boolean active) {
        return orderRepository.getTotalRevenueOfMonth(month, year, active);
    }


}
