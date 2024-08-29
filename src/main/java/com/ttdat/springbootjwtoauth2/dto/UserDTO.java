package com.ttdat.springbootjwtoauth2.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    UUID userId;
    String fullName;
    String email;
    String password;
    Set<RoleDTO> roles;
}
