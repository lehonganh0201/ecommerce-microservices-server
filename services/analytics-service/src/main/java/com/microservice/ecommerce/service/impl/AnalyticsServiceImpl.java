package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.client.OrderClient;
import com.microservice.ecommerce.client.OrderResponse;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.PageResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.response.AnalyticsResponse;
import com.microservice.ecommerce.model.response.RevenueStatistics;
import com.microservice.ecommerce.service.AnalyticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    23/03/2025 at 10:55 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class AnalyticsServiceImpl implements AnalyticsService {
    OrderClient orderClient;

    @Override
    public GlobalResponse<AnalyticsResponse<?>> getRevenueStatistics(LocalDate startDate, LocalDate endDate, String groupBy, Integer interval) {
        if (interval == null || interval < 1) interval = 1;

        GlobalResponse<PageResponse<OrderResponse>> response = orderClient.findAllOrders(
                0, 1000, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        List<OrderResponse> orders = response.data().content();

        Map<String, Double> groupedRevenue = switch (groupBy.toLowerCase()) {
            case "daily" -> groupByDate(orders, interval);
            case "monthly" -> groupByMonth(orders, interval);
            case "yearly" -> groupByYear(orders, interval);
            default -> throw new IllegalArgumentException("Invalid groupBy value: " + groupBy);
        };

        List<RevenueStatistics> statistics = groupedRevenue.entrySet().stream()
                .map(entry -> new RevenueStatistics(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new GlobalResponse<>(
                Status.SUCCESS,
                new AnalyticsResponse<>(
                        statistics
                )
        );
    }

    private Map<String, Double> groupByYear(List<OrderResponse> orders, int interval) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> String.valueOf((order.createdDate().getYear() / interval) * interval),
                        TreeMap::new,
                        Collectors.summingDouble(OrderResponse::totalAmount)
                ));
    }

    private Map<String, Double> groupByMonth(List<OrderResponse> orders, int interval) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> {
                            int baseMonth = (order.createdDate().getMonthValue() - 1) / interval * interval + 1;
                            return String.format("%d-%02d", order.createdDate().getYear(), baseMonth);
                        },
                        TreeMap::new,
                        Collectors.summingDouble(OrderResponse::totalAmount)
                ));
    }

    private Map<String, Double> groupByDate(List<OrderResponse> orders, int interval) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.createdDate()
                                .toLocalDate()
                                .withDayOfMonth((order.createdDate().getDayOfMonth() - 1) / interval * interval + 1)
                                .toString(),
                        TreeMap::new,
                        Collectors.summingDouble(OrderResponse::totalAmount)
                ));
    }
}
