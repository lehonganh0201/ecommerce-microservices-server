package com.microservice.ecommerce.model.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 9:09 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public record ProfileRequest(
        MultipartFile avatar,
        Boolean gender,
        LocalDateTime dateOfBirth
) {
}
