package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dto.CreateUserRequest;
import com.qualaboa.msauth.dto.UpdateUserRequest;
import com.qualaboa.msauth.entities.User;

import java.time.LocalDateTime;

public class UserMapper {
    public static User toEntity(CreateUserRequest userRequest) {
        if (userRequest == null) return null;
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setUserTypeEnum(userRequest.getUserTypeEnum());
        user.setRoleEnum(userRequest.getRoleEnum());

        return user;
    }

    public static User toEntity(UpdateUserRequest userRequest, User entity) {
        if (userRequest == null) return null;
        User user = new User();
        user.setName(userRequest.getName() == null ? entity.getName() : userRequest.getName());
        user.setEmail(userRequest.getEmail() == null ? entity.getEmail() : userRequest.getEmail());
        user.setPassword(userRequest.getPassword() == null ? entity.getPassword() : userRequest.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }
}
