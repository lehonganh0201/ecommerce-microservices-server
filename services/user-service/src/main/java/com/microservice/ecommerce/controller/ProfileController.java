package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.ProfileRequest;
import com.microservice.ecommerce.model.response.ProfileResponse;
import com.microservice.ecommerce.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 9:16 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequestMapping(Endpoint.Profile.PREFIX)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {
    ProfileService profileService;

    @PostMapping()
    public ResponseEntity<GlobalResponse<ProfileResponse>> createProfile(
            @ModelAttribute ProfileRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(profileService.createProfile(request, jwt));
    }

    @GetMapping()
    public ResponseEntity<GlobalResponse<List<ProfileResponse>>> findCurrentUserProfile(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(profileService.findCurrentUserProfile(jwt));
    }

    @GetMapping(Endpoint.Profile.GET_BY_ID)
    public ResponseEntity<GlobalResponse<ProfileResponse>> findProfileById(
            @PathVariable(name = "profileId") Integer profileId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(profileService.findProfileId(profileId, jwt));
    }

    @PutMapping(Endpoint.Profile.UPDATE_BY_ID)
    public ResponseEntity<GlobalResponse<ProfileResponse>> updateProfile(
            @PathVariable(name = "profileId") Integer profileId,
            @ModelAttribute ProfileRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity
                .ok(profileService.updateProfile(profileId, request, jwt));
    }
}
