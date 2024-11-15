package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dataContract.dtos.user.UserCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.user.UserUpdateDTO;
import com.qualaboa.msauth.dataContract.entities.User;

import java.time.LocalDateTime;

public class UserMapper {
    public static User toEntity(UserCreateDTO userRequest) {
        if (userRequest == null) return null;
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRoleEnum(userRequest.getRoleEnum());

        return user;
    }

    public static User toEntity(UserUpdateDTO userRequest, User entity) {
        if (userRequest == null) return null;
        User user = new User();
        user.setId(entity.getId());
        user.setName(userRequest.getName() == null ? entity.getName() : userRequest.getName());
        user.setEmail(userRequest.getEmail() == null ? entity.getEmail() : userRequest.getEmail());
        user.setPassword(userRequest.getPassword() == null ? entity.getPassword() : userRequest.getPassword());
        user.setRoleEnum(entity.getRoleEnum());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }
}
