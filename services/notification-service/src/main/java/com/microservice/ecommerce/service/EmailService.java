package com.microservice.ecommerce.service;

import com.microservice.ecommerce.document.NotificationType;
import com.microservice.ecommerce.message.ProductPriceResponse;
import jakarta.mail.MessagingException;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 5:06 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface EmailService {

    void sendEmail(
            String destinationEmail,
            String customerName,
            Double amount,
            String orderReference,
            List<ProductPriceResponse> products,
            NotificationType notificationType
    ) throws MessagingException;
}
