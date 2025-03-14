package com.microservice.ecommerce.model.mapper;

import com.microservice.ecommerce.model.dto.request.PaymentRequest;
import com.microservice.ecommerce.model.dto.response.PaymentResponse;
import com.microservice.ecommerce.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 8:01 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMapper {
    Payment toPayment(PaymentRequest request);

    PaymentResponse toPaymentResponse(Payment payment);
}
