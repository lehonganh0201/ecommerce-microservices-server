package com.microservice.ecommerce.controller;

import com.microservice.ecommerce.constant.Endpoint;
import com.microservice.ecommerce.model.global.GlobalResponse;
import com.microservice.ecommerce.model.request.UserRequest;
import com.microservice.ecommerce.model.response.UserResponse;
import com.microservice.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.User.PREFIX)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @Operation(summary = "Tạo người dùng mới", description = "API này cho phép tạo người dùng mới dựa trên thông tin gửi lên. Người dùng cần phải xác thực.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo người dùng thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @PostMapping
    public ResponseEntity<GlobalResponse<UserResponse>> createUser(
            @RequestBody(description = "Thông tin người dùng cần tạo") @Valid UserRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(userService.createUser(request, jwt));
    }

    @Operation(summary = "Lấy thông tin người dùng hiện tại", description = "API này lấy thông tin người dùng hiện tại dựa trên token xác thực JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content)
    })
    @GetMapping(Endpoint.User.ME)
    public ResponseEntity<GlobalResponse<UserResponse>> findCurrentUser(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(userService.findCurrentUser(jwt));
    }

    @Operation(summary = "Cập nhật thông tin người dùng", description = "API này cho phép cập nhật thông tin người dùng hiện tại. Có thể truyền thêm ID của địa chỉ cần cập nhật.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thông tin thành công",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content),
            @ApiResponse(responseCode = "401", description = "Người dùng chưa xác thực", content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy địa chỉ cần cập nhật", content = @Content)
    })
    @PutMapping
    public ResponseEntity<GlobalResponse<UserResponse>> updateCurrentUser(
            @RequestParam(name = "addressId", required = false) Integer addressId,
            @RequestBody(description = "Thông tin người dùng cần cập nhật") @Valid UserRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(userService.updateUser(addressId, request, jwt));
    }
}
