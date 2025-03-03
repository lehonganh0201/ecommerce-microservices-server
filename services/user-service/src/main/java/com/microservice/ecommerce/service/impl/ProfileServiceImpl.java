package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.model.entity.UserProfile;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.global.Status;
import com.microservice.ecommerce.model.request.ProfileRequest;
import com.microservice.ecommerce.model.response.ProfileResponse;
import com.microservice.ecommerce.repository.UserProfileRepository;
import com.microservice.ecommerce.repository.UserRepository;
import com.microservice.ecommerce.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    03/03/2025 at 9:19 PM
 * Project:       ecommerce-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImpl implements ProfileService {
    UserProfileRepository profileRepository;
    UserRepository userRepository;

    private static final String BASE_DIRECTORY = "./resource/images/user-avatar/";

    @Override
    public GlobalResponse<ProfileResponse> createProfile(ProfileRequest request, Jwt jwt) {
        var user = userRepository.findById(jwt.getSubject());

        if (!user.isPresent()) {
            throw new EntityNotFoundException("Cannot found user request, please logout and try again");
        }

        try {
            File directory = new File(BASE_DIRECTORY);

            if (!directory.exists()) {
                log.warn("WARNING WRONG FILE PATH");
                Path filePath = Paths.get(BASE_DIRECTORY);
                log.error(filePath.toAbsolutePath());
                return null;
            }

            String originalFilename = StringUtils.cleanPath(request.avatar().getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            String newFileName = UUID.randomUUID() + fileExtension;
            Path filePath = Paths.get(BASE_DIRECTORY + newFileName);


            Files.write(filePath, request.avatar().getBytes());

            UserProfile profile = UserProfile.builder()
                    .user(user.get())
                    .avatarUrl(filePath.toAbsolutePath().toString())
                    .gender(request.gender())
                    .dateOfBirth(request.dateOfBirth())
                    .build();

            profile = profileRepository.save(profile);

            return new GlobalResponse<>(
              Status.SUCCESS,
              new ProfileResponse(
                      profile.getId(),
                      profile.getAvatarUrl(),
                      profile.isGender(),
                      profile.getDateOfBirth()
              )
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to store avatar");
        }
    }
}
