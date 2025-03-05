package com.microservice.ecommerce.service.impl;

import com.microservice.ecommerce.exception.BusinessException;
import com.microservice.ecommerce.mapper.ProfileMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    ProfileMapper profileMapper;

    private static final String BASE_DIRECTORY = "./resource/images/user-avatar/";
    private static final String ROOT_DIRECTORY = System.getProperty("user.dir");

    @Override
    public GlobalResponse<ProfileResponse> createProfile(ProfileRequest request, Jwt jwt) {
        var user = userRepository.findById(jwt.getSubject());

        if (!user.isPresent()) {
            throw new EntityNotFoundException("Cannot found user request, please logout and try again");
        }

        try {
            File directory = new File(BASE_DIRECTORY);

            if (!directory.exists()) {
                directory.mkdir();
            }

            String originalFilename = StringUtils.cleanPath(request.avatar().getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            String newFileName = UUID.randomUUID() + fileExtension;
            Path filePath = Paths.get(BASE_DIRECTORY + newFileName);


            Files.write(filePath, request.avatar().getBytes());

            UserProfile profile = UserProfile.builder()
                    .user(user.get())
                    .avatarUrl(filePath.toString())
                    .gender(request.gender())
                    .dateOfBirth(request.dateOfBirth())
                    .build();

            profile = profileRepository.save(profile);

            return new GlobalResponse<>(
              Status.SUCCESS,
              new ProfileResponse(
                      profile.getId(),
                      ROOT_DIRECTORY + profile.getAvatarUrl(),
                      profile.isGender(),
                      profile.getDateOfBirth()
              )
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to store avatar");
        }
    }

    @Override
    public GlobalResponse<List<ProfileResponse>> findCurrentUserProfile(Jwt jwt) {
        List<UserProfile> profiles = profileRepository.findAllByUserId(jwt.getSubject());
//        var responses = profiles.stream()
//                .map(profile -> {
//                    var response = new ProfileResponse(
//                            profile.getId(),
//                            ROOT_DIRECTORY + profile.getAvatarUrl(),
//                            profile.isGender(),
//                            profile.getDateOfBirth()
//                    );
//                    return response;
//                })
//                .collect(Collectors.toList()
//                );

        if (profiles.isEmpty()) {
            throw new EntityNotFoundException("Not found current user profile");
        }

        var responses = profiles.stream()
                .map(profile -> {
                    ProfileResponse response = new ProfileResponse(
                              profile.getId(),
                            ROOT_DIRECTORY + profile.getAvatarUrl(),
                            profile.isGender(),
                            profile.getDateOfBirth()
                    );

                    return response;
        }).collect(Collectors.toList());

        return new GlobalResponse<>(
                Status.SUCCESS,
                responses
        );
    }

    @Override
    public GlobalResponse<ProfileResponse> findProfileId(Integer profileId, Jwt jwt) {
        UserProfile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found profile with ID:: " + profileId));

        var currentUserProfiles = profileRepository.findAllByUserId(jwt.getSubject());

        boolean flag = currentUserProfiles.stream()
                .anyMatch(userProfile -> userProfile.equals(profile));

        if (flag) {
            return new GlobalResponse<>(
                    Status.SUCCESS,
                    new ProfileResponse(
                            profile.getId(),
                            ROOT_DIRECTORY + profile.getAvatarUrl(),
                            profile.isGender(),
                            profile.getDateOfBirth()
                    )
            );
        }else {
            return new GlobalResponse<>(
                    Status.ERROR,
                    null
            );
        }
    }

    @Override
    public GlobalResponse<ProfileResponse> updateProfile(Integer profileId, ProfileRequest request, Jwt jwt) {
        UserProfile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Not found any profile with provided ID."));

        var currentUserProfiles = profileRepository.findAllByUserId(jwt.getSubject());

        boolean flag = currentUserProfiles.stream()
                .anyMatch(userProfile -> userProfile.getId().equals(profileId));

        if (!flag) {
            throw new BusinessException("This profile not belong to own");
        }

        Path filePath = null;

        if (!request.avatar().isEmpty()) {
            try {
                File directory = new File(BASE_DIRECTORY);

                if (!directory.exists()) {
                    log.warn("WARNING WRONG FILE PATH");
                    return null;
                }

                // 🛑 Xóa ảnh cũ trước khi lưu ảnh mới
                if (profile.getAvatarUrl() != null) {
                    File oldFile = new File(profile.getAvatarUrl());
                    if (oldFile.exists() && oldFile.isFile()) {
                        if (oldFile.delete()) {
                            log.info("Deleted old avatar: " + profile.getAvatarUrl());
                        } else {
                            log.warn("Failed to delete old avatar: " + profile.getAvatarUrl());
                        }
                    }
                }

                // 🆕 Lưu ảnh mới
                String originalFilename = StringUtils.cleanPath(request.avatar().getOriginalFilename());
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID() + fileExtension;
                filePath = Paths.get(BASE_DIRECTORY + newFileName);

                Files.write(filePath, request.avatar().getBytes());

            } catch (IOException e) {
                throw new RuntimeException("Failed to store avatar", e);
            }
        }

        if (request.gender() != null) {
            profile.setGender(request.gender());
        }

        if (request.dateOfBirth() != null) {
            profile.setDateOfBirth(request.dateOfBirth());
        }

        if (!request.avatar().isEmpty()) {
            profile.setAvatarUrl(filePath.toString());
        }

        profile = profileRepository.save(profile);

        return new GlobalResponse<>(
                Status.SUCCESS,
                new ProfileResponse(
                        profile.getId(),
                        ROOT_DIRECTORY + profile.getAvatarUrl(),
                        profile.isGender(),
                        profile.getDateOfBirth()
                )
        );
    }

}
