package com.ttdat.springbootjwtoauth2.mapper;

import com.ttdat.springbootjwtoauth2.dto.UserDTO;
import com.ttdat.springbootjwtoauth2.entity.Role;
import com.ttdat.springbootjwtoauth2.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToRoleName")
    UserDTO toUserDTO(User user);

    @Mapping(target = "roles", ignore = true)
    User toUser(UserDTO userDTO);

    @Named("rolesToRoleName")
    default Set<String> rolesToRoleName(Set<Role> roles){
        return roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
    }
}
