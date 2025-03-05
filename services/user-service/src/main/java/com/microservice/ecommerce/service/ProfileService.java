package com.microservice.ecommerce.service;

import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.ProfileRequest;
import com.microservice.ecommerce.model.response.ProfileResponse;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 9:17 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface ProfileService {
    GlobalResponse<ProfileResponse> createProfile(ProfileRequest request, Jwt jwt);

    GlobalResponse<ProfileResponse> findCurrentUserProfile(Jwt jwt);

    GlobalResponse<ProfileResponse> findProfileId(Integer profileId, Jwt jwt);

    GlobalResponse<ProfileResponse> updateProfile(Integer profileId, ProfileRequest request, Jwt jwt);
}
