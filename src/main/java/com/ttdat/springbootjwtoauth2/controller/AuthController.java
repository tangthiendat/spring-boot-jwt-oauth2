package com.ttdat.springbootjwtoauth2.controller;

import com.ttdat.springbootjwtoauth2.dto.ApiResponse;
import com.ttdat.springbootjwtoauth2.dto.UserDTO;
import com.ttdat.springbootjwtoauth2.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ApiResponse.<UserDTO>builder()
                .status(HttpStatus.CREATED.value())
                .data(authService.register(userDTO))
                .build();
    }
}
