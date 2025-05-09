package com.totfd.lms.mapper;

import com.totfd.lms.dto.user.request.UserRequestDTO;
import com.totfd.lms.dto.user.response.UserResponseDTO;
import com.totfd.lms.entity.Role;
import com.totfd.lms.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.name", target = "role")
    UserResponseDTO toResponseDTO(Users users);

    @Mapping(source = "role", target = "role")
    Users toEntity(
            UserRequestDTO requestDTO);

    default Role map(String roleName) {
        if (roleName == null) return null;
        Role role = new Role();
        role.setName(roleName);
        return role;
    }
}
