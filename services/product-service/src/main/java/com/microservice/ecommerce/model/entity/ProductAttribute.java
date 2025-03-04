package com.microservice.ecommerce.model.entity;

import com.microservice.ecommerce.constant.AttributeType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    04/03/2025 at 12:36 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_attributes")
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Enumerated(EnumType.STRING)
    AttributeType type;

    @Column(nullable = false)
    String value;

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    ProductVariant productVariant;
}
