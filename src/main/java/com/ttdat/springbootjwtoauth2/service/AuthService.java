package com.ttdat.springbootjwtoauth2.service;

import com.ttdat.springbootjwtoauth2.dto.ApiResponse;
import com.ttdat.springbootjwtoauth2.dto.AuthRequest;
import com.ttdat.springbootjwtoauth2.dto.AuthResponse;
import com.ttdat.springbootjwtoauth2.dto.UserDTO;
import com.ttdat.springbootjwtoauth2.entity.Role;
import com.ttdat.springbootjwtoauth2.entity.User;
import com.ttdat.springbootjwtoauth2.mapper.UserMapper;
import com.ttdat.springbootjwtoauth2.repository.RoleRepository;
import com.ttdat.springbootjwtoauth2.repository.UserRepository;
import com.ttdat.springbootjwtoauth2.util.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    JwtDecoder jwtDecoder;


    public ApiResponse<UserDTO> register(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(role));
        return ApiResponse.<UserDTO>builder()
                .status(HttpStatus.CREATED.value())
                .data(userMapper.toUserDTO(userRepository.save(user)))
                .build();
    }

    public ApiResponse<AuthResponse> login(AuthRequest authRequest, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setSecure(true); //HTTPS
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setAttribute("SameSite", "Strict");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days

        response.addCookie(refreshTokenCookie);

        return ApiResponse.<AuthResponse>builder()
                .status(HttpStatus.OK.value())
                .data(AuthResponse.builder().accessToken(accessToken).build())
                .build();
    }

    public ApiResponse<AuthResponse> refreshToken(String token){
        try{
            Jwt refreshToken = jwtDecoder.decode(token);
            String username = jwtUtils.getUsername(refreshToken);
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return ApiResponse.<AuthResponse>builder()
                    .status(HttpStatus.OK.value())
                    .data(AuthResponse.builder().
                            accessToken(jwtUtils.generateAccessToken(user))
                            .build())
                    .build();
        }catch (JwtException e){
            return ApiResponse.<AuthResponse>builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid refresh token")
                    .build();
        }

    }

}
