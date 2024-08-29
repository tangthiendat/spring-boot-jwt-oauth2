package com.ttdat.springbootjwtoauth2.mapper;

import com.ttdat.springbootjwtoauth2.dto.RoleDTO;
import com.ttdat.springbootjwtoauth2.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleDTO toRoleDTO(Role role);
    Role toRole(RoleDTO roleDTO);
}
