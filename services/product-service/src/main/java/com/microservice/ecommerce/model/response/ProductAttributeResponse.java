package com.microservice.ecommerce.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    06/03/2025 at 12:44 AM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductAttributeResponse {
    UUID id;
    String type;
    String value;
}