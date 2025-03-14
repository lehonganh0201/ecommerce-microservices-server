package com.microservice.ecommerce.model.entity;

import com.microservice.ecommerce.constant.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    13/03/2025 at 7:08 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    UUID id;

    Double amount;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Column(nullable = false, updatable = false, unique = true)
    UUID orderId;

    String bankCode;

    String language;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    LocalDateTime lastModifiedDate;
}
