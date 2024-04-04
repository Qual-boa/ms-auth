package com.qualaboa.msauth.dto;

import com.qualaboa.msauth.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "Name can't be blank")
    private String name;

    @NotBlank(message = "Email can't be blank")
    private String email;

    @NotBlank(message = "Password can't be blank")
    private String password;

    public UpdateUserRequest(User entity){
        name = entity.getName();
        email = entity.getEmail();
        password = entity.getPassword();
    }
}
