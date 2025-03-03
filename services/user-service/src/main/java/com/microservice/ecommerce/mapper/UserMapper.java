package com.microservice.ecommerce.mapper;

import com.microservice.ecommerce.model.entity.User;
import com.microservice.ecommerce.model.request.UserRequest;
import com.microservice.ecommerce.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 8:23 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User toUser(UserRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(UserRequest request, @MappingTarget User user);
}
