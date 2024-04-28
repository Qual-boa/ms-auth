package com.qualaboa.msauth.dto;

import com.qualaboa.msauth.entities.User;
import com.qualaboa.msauth.entities.enums.RoleEnum;
import com.qualaboa.msauth.entities.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String name;
    private String email;

    private UserTypeEnum userTypeEnum;
    private RoleEnum roleEnum;

    public UserResponseDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        userTypeEnum = entity.getUserTypeEnum();
        roleEnum = entity.getRoleEnum();
    }
}
