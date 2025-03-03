package com.microservice.ecommerce.mapper;

import com.microservice.ecommerce.model.entity.UserProfile;
import com.microservice.ecommerce.model.request.ProfileRequest;
import com.microservice.ecommerce.model.response.ProfileResponse;
import org.mapstruct.*;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 11:22 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileMapper {
    @Mapping(source = "avatarUrl", target = "avatar")
    ProfileResponse toProfileResponse(UserProfile profile);

}
