package com.ttdat.springbootjwtoauth2.util;

import com.ttdat.springbootjwtoauth2.entity.Role;
import com.ttdat.springbootjwtoauth2.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUtils {

    ApplicationContext applicationContext;

    public String generateAccessToken(User user) {
        JwtEncoder jwtEncoder = applicationContext.getBean(JwtEncoder.class);
        Set<String> userRoles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getUsername())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(5, ChronoUnit.MINUTES))
                .claim("fullName", user.getFullName())
                .claim("role", userRoles)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String getUsername(Jwt jwtToken) {
        return jwtToken.getSubject();
    }

    private boolean isTokenExpired(Jwt jwtToken) {
        return Objects.requireNonNull(jwtToken.getExpiresAt()).isBefore(Instant.now());
    }

    public boolean isTokenValid(Jwt jwtToken, UserDetails userDetails) {
        final String userEmail = getUsername(jwtToken);
        return !isTokenExpired(jwtToken) && userEmail.equals(userDetails.getUsername());
    }

}
