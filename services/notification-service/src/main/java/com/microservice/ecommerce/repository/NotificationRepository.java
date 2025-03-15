package com.microservice.ecommerce.repository;

import com.microservice.ecommerce.document.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 5:42 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface NotificationRepository extends MongoRepository<Notification, UUID> {
}
