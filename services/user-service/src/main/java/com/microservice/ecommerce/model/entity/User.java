package com.microservice.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 7:14 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    String id;

    @Column(nullable = false, unique = true, updatable = false)
    String username;

    @Column(nullable = false, unique = true)
    String email;

    String phoneNumber;

    @ManyToOne
    Role role;
}
