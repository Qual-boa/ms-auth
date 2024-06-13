package com.qualaboa.msauth.dataContract.dtos.user;

import com.qualaboa.msauth.dataContract.entities.User;
import com.qualaboa.msauth.dataContract.enums.RoleEnum;
import com.qualaboa.msauth.dataContract.enums.UserTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    @Email(message = "Email should be valid")
    private String email;

    private String name;

    @NotBlank
    @Size(min = 8, message = "Password requires at least 8 caracters")
    private String password;

    @NotNull(message = "Role Enum can't be null")
    private RoleEnum roleEnum;
    
    private UUID establishmentId;

    public UserCreateDTO(User entity) {
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.roleEnum = entity.getRoleEnum();
    }
}
