package com.ttdat.springbootjwtoauth2.mapper;

import com.ttdat.springbootjwtoauth2.dto.UserDTO;
import com.ttdat.springbootjwtoauth2.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
}
