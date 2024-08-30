package com.ttdat.springbootjwtoauth2.service;

import com.ttdat.springbootjwtoauth2.dto.UserDTO;
import com.ttdat.springbootjwtoauth2.entity.Role;
import com.ttdat.springbootjwtoauth2.entity.User;
import com.ttdat.springbootjwtoauth2.mapper.UserMapper;
import com.ttdat.springbootjwtoauth2.repository.RoleRepository;
import com.ttdat.springbootjwtoauth2.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public UserDTO register(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(role));
        return userMapper.toUserDTO(userRepository.save(user));
    }

}
