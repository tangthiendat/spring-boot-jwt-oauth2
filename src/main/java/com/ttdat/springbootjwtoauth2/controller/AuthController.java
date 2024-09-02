package com.ttdat.springbootjwtoauth2.controller;

import com.ttdat.springbootjwtoauth2.dto.ApiResponse;
import com.ttdat.springbootjwtoauth2.dto.AuthRequest;
import com.ttdat.springbootjwtoauth2.dto.AuthResponse;
import com.ttdat.springbootjwtoauth2.dto.UserDTO;
import com.ttdat.springbootjwtoauth2.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody UserDTO userDTO) {
        return authService.register(userDTO);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest,
                                           HttpServletResponse response) {
        return authService.login(authRequest, response);
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@CookieValue("refresh_token") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
