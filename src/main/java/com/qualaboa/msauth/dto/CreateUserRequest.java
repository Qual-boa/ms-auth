package com.qualaboa.msauth.dto;

import com.qualaboa.msauth.entities.User;
import com.qualaboa.msauth.entities.enums.RoleEnum;
import com.qualaboa.msauth.entities.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 8, message = "Name requires at least 8 caracters")
    private String name;

    @NotBlank
    @Size(min = 8, message = "Password requires at least 8 caracters")
    private String password;

    @NotNull(message = "User Type Enum can't be null")
    private UserTypeEnum userTypeEnum;

    @NotNull(message = "Role Enum can't be null")
    private RoleEnum roleEnum;

    public CreateUserRequest(User entity) {
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.userTypeEnum = entity.getUserTypeEnum();
        this.roleEnum = entity.getRoleEnum();
    }

}
