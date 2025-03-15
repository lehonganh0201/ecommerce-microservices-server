package com.microservice.ecommerce.service;

import com.microservice.ecommerce.document.Notification;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    15/03/2025 at 5:44 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface NotificationService {
    void saveNotification(Notification notification);
}
