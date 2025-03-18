package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.ProfileRequest;
import com.microservice.ecommerce.model.response.ProfileResponse;
import com.microservice.ecommerce.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Profile.PREFIX)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {
    ProfileService profileService;

    @Operation(summary = "Tạo hồ sơ người dùng", description = "API này cho phép tạo hồ sơ người dùng mới dựa trên thông tin gửi lên dưới dạng form-data. Yêu cầu xác thực JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo hồ sơ thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GlobalResponse<ProfileResponse>> createProfile(
            @ModelAttribute ProfileRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(profileService.createProfile(request, jwt));
    }

    @Operation(summary = "Lấy hồ sơ người dùng hiện tại", description = "API này trả về hồ sơ của người dùng hiện tại dựa trên token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy hồ sơ thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<GlobalResponse<ProfileResponse>> findCurrentUserProfile(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(profileService.findCurrentUserProfile(jwt));
    }

    @Operation(summary = "Lấy hồ sơ theo ID", description = "API này lấy thông tin hồ sơ dựa trên ID hồ sơ. Yêu cầu xác thực JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy hồ sơ thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy hồ sơ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @GetMapping(Endpoint.Profile.GET_BY_ID)
    public ResponseEntity<GlobalResponse<ProfileResponse>> findProfileById(
            @PathVariable(name = "profileId") Integer profileId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(profileService.findProfileId(profileId, jwt));
    }

    @Operation(summary = "Cập nhật hồ sơ người dùng", description = "API này cho phép cập nhật thông tin hồ sơ người dùng dựa trên ID. Dữ liệu gửi lên dưới dạng form-data. Yêu cầu xác thực JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật hồ sơ thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy hồ sơ cần cập nhật", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @PutMapping(value = Endpoint.Profile.UPDATE_BY_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GlobalResponse<ProfileResponse>> updateProfile(
            @PathVariable(name = "profileId") Integer profileId,
            @ModelAttribute ProfileRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(profileService.updateProfile(profileId, request, jwt));
    }
}
