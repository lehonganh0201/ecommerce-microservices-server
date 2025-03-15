package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.document.Notification;
import com.microservice.ecommerce.repository.NotificationRepository;
import com.microservice.ecommerce.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 11:46 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;


    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }
}
