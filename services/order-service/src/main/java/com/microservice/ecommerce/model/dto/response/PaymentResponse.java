package com.microservice.ecommerce.model.dto.response;

import com.microservice.ecommerce.constant.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 7:45 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse{
    UUID id;
    Double amount;
    PaymentMethod paymentMethod;
    LocalDateTime createdDate;
    String bankCode;
    String language;
    String paymentUrl;
    String appUrl;
}
