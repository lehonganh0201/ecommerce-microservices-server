package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.client.PaymentClient;
import com.microservice.ecommerce.client.ProductClient;
import com.microservice.ecommerce.client.PurchaseRequest;
import com.microservice.ecommerce.constant.OrderStatus;
import com.microservice.ecommerce.constant.PaymentMethod;
import com.microservice.ecommerce.exception.BusinessException;
import com.microservice.ecommerce.message.OrderConfirmation;
import com.microservice.ecommerce.message.OrderProducer;
import com.microservice.ecommerce.model.dto.request.OrderItemRequest;
import com.microservice.ecommerce.model.dto.request.OrderRequest;
import com.microservice.ecommerce.model.dto.request.PaymentRequest;
import com.microservice.ecommerce.model.dto.response.OrderItemResponse;
import com.microservice.ecommerce.model.dto.response.OrderResponse;
import com.microservice.ecommerce.model.dto.response.PaymentResponse;
import com.microservice.ecommerce.model.dto.response.ProductPriceResponse;
import com.microservice.ecommerce.model.entity.Order;
import com.microservice.ecommerce.model.entity.OrderItem;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.repository.OrderRepository;
import com.microservice.ecommerce.service.OrderService;
import com.microservice.ecommerce.util.GeneratorUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    09/03/2025 at 5:42 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    ProductClient productClient;
    PaymentClient paymentClient;

    OrderProducer orderProducer;

    @Override
    public GlobalResponse<OrderResponse> createOrder(OrderRequest request, Jwt jwt) {
        log.info("RECEIVE ORDER CREATE: {}", request.items());
        boolean isStockAvailable = productClient.checkStock(request.items()).getBody();

        if (!isStockAvailable) {
            throw new BusinessException("Không đủ hàng trong kho.");
        }

        List<UUID> variantIds = request.items().stream()
                .map(OrderItemRequest::variantId)
                .collect(Collectors.toList());

        Map<UUID, Integer> orderedQuantities = request.items().stream()
                .collect(Collectors.toMap(OrderItemRequest::variantId, OrderItemRequest::quantity));

        List<ProductPriceResponse> prices = productClient.getProductPrices(new PurchaseRequest(
                variantIds,
                orderedQuantities
        )).getBody();

        Map<UUID, Double> priceMap = prices.stream()
                .collect(Collectors.toMap(ProductPriceResponse::variantId, ProductPriceResponse::price));

        double totalAmount = request.items().stream()
                .mapToDouble(item -> priceMap.getOrDefault(item.variantId(), 0.0) * item.quantity())
                .sum();

        Order order = orderRepository.save(Order.builder()
                .paymentMethod(request.paymentMethod())
                .reference(GeneratorUtil.generatorReference())
                .status(OrderStatus.PENDING)
                .userId(jwt.getSubject())
                .totalAmount(totalAmount)
                .build());

        List<OrderItem> orderItems = new ArrayList<>();
        for (var item : request.items()) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .price(priceMap.get(item.variantId()))
                    .quantity(item.quantity())
                    .productId(item.productId())
                    .variantId(item.variantId())
                    .build();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        order = orderRepository.save(order);

        orderProducer.sendOrderConfirmation(new OrderConfirmation(
            order.getReference(),
            totalAmount,
            order.getPaymentMethod().name(),
            jwt.getClaims().get("name").toString(),
            jwt.getClaims().get("email").toString(),
            prices
        ));

        orderProducer.sendUpdateStock(request.items());

        PaymentResponse paymentResponse;

        if (request.paymentMethod().equals(PaymentMethod.MOMO)) {
            paymentResponse = paymentClient.createMoMoPayment(new PaymentRequest(
                    totalAmount,
                    request.paymentMethod(),
                    order.getId(),
                    request.bankCode() != null ? request.bankCode() : null,
                    request.language()
            )).getBody().data();
        }
        else if (request.paymentMethod().equals(PaymentMethod.VN_PAY)) {
            paymentResponse = paymentClient.createVNPayPayment(new PaymentRequest(
                    totalAmount,
                    request.paymentMethod(),
                    order.getId(),
                    request.bankCode() != null ? request.bankCode() : null,
                    request.language()
            )).getBody().data();
        }else {
            paymentResponse = paymentClient.createCODPayment(new PaymentRequest(
                    totalAmount,
                    request.paymentMethod(),
                    order.getId(),
                    null,
                    request.language()
            )).getBody().data();
        }

        return new GlobalResponse<>(
                Status.SUCCESS,
                new OrderResponse(
                        order.getReference(),
                        order.getStatus(),
                        order.getPaymentMethod(),
                        order.getTotalAmount(),
                        order.getOrderItems().stream()
                                .map(item -> new OrderItemResponse(
                                        item.getVariantId(),
                                        item.getQuantity(),
                                        item.getPrice()
                                ))
                        .collect(Collectors.toList()),
                        paymentResponse
                )
        );
    }

    @Override
    public GlobalResponse<List<OrderResponse>> findOwnOrders(String type, Jwt jwt) {
        String userId = jwt.getSubject();

        List<Order> orders = orderRepository.findAllByUserIdAndStatus(userId, type);

        return new GlobalResponse<>(
                Status.SUCCESS,
                getOrderResponse(orders)
        );
    }

    @Override
    public GlobalResponse<OrderResponse> findOrderById(UUID orderId, Jwt jwt) {
        String userId = jwt.getSubject();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng"));

        if (!order.getUserId().equals(userId)) {
            throw new AuthorizationDeniedException("Bạn không có quyền thực hiện hành động này.");
        }

        return new GlobalResponse<>(
                Status.SUCCESS,
                getOrderResponse(List.of(order)).get(0)
        );
    }

    @Override
    public GlobalResponse<OrderResponse> canceledOrderById(UUID orderId, String orderStatus , Jwt jwt) {
        String userId = jwt.getSubject();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng"));

        if (!order.getUserId().equals(userId)) {
            throw new AuthorizationDeniedException("Bạn không có quyền thực hiện hành động này.");
        }

        try {
            OrderStatus statusEnum = OrderStatus.valueOf(orderStatus.toUpperCase());
            order.setStatus(statusEnum);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Trạng thái đơn hàng không hợp lệ: " + orderStatus);
        }

        order = orderRepository.save(order);

        return new GlobalResponse<>(
                Status.SUCCESS,
                getOrderResponse(List.of(order)).get(0)
        );
    }

    @Override
    public GlobalResponse<OrderResponse> getByReference(String reference, Jwt jwt) {
        Order order = orderRepository.findByReference(reference)
                .orElseThrow(() -> new EntityNotFoundException("Not found entity by reference"));

        if (!order.getUserId().equals(jwt.getSubject())) {
            throw new AuthorizationDeniedException("Bạn không có quyền thực hiện hành động này.");
        }

        return new GlobalResponse<>(
                Status.SUCCESS,
                getOrderResponse(List.of(order)).get(0)
        );
    }

    private List<OrderResponse> getOrderResponse(List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderResponse(
                        order.getReference(),
                        order.getStatus(),
                        order.getPaymentMethod(),
                        order.getTotalAmount(),
                        order.getOrderItems().stream()
                                .map(item -> new OrderItemResponse(
                                        item.getVariantId(),
                                        item.getQuantity(),
                                        item.getPrice()
                                ))
                                .collect(Collectors.toList()),
                        null
                ))
                .collect(Collectors.toList());
    }
}
